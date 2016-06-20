package com.gilt.omar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gilt.omar.Finish;

public class App 
{

	private static int numberOfColors;
	private static final String NO_SOLUTION_EXISTS = "No solution exists";

	private static Map<Integer, Finish> finalPaints= new HashMap<>();

	static List<Customer> customers=new ArrayList<Customer>();


	public static void main( String[] args ) throws Exception
	{
		if (args.length != 1) {
			System.err.println("Wrong Number of Argument. "
					+ "Please provide the path for the input file as the first argument!");
			System.exit(1);
		}

		List<String> inputs=loadFileToList(args[0]);
		numberOfColors=Integer.parseInt(inputs.get(0).substring(0,1));
		for(int i=1;i<inputs.size();i++){
			Customer customer= inputToCustomerPreferedColors(inputs.get(i).replaceAll("\\s+",""));
			customers.add(customer);
		}

		rearangePaintOrder(customers);
		System.out.println(makePaints());
	}

	public static String makePaints() throws Exception{

		int distictPaint=getDistincPaint(customers);
		System.out.println(distictPaint);
		if(customers.size()>distictPaint){

			return NO_SOLUTION_EXISTS;
		}
		for (Customer customer : customers) {  
			if(customer.getnumOfPreferedPaints() == 1){
				Paint fixedPaint = finalisePaint(customer, null);
				if (fixedPaint == null) {

				}

				finalPaints.put(fixedPaint.getColor(), fixedPaint.getFinish());
			}
			else {
				List<Paint> paintCandidates = new ArrayList<>();        
				Paint fixedPaint = finalisePaint(customer, paintCandidates);

				if (fixedPaint != null) {
					continue;
				}
				else if (paintCandidates.isEmpty()) {
					return NO_SOLUTION_EXISTS;
				}

				Paint paintToSelect = paintCandidates.get(0);
				for (Paint paint : paintCandidates) {
					if (paint.getFinish().equals(Finish.GLOSS)) {
						paintToSelect = paint;
					}
				}

				finalPaints.put(paintToSelect.getColor(), paintToSelect.getFinish());
			}
		}

		return createOutput(finalPaints, numberOfColors);
	}


	private static Paint finalisePaint(Customer customer, List<Paint> paintCandidates) {




		for (Paint paint : customer.getPreferedPaints()) {
			Integer color = paint.getColor();
			Finish finish = paint.getFinish();
			Finish fixedFinish = finalPaints.get(color);



			if (customer.getnumOfPreferedPaints()== 1) {

				if (fixedFinish == null || fixedFinish.equals(finish)) {
					// there is no finish fixed for this color yet,
					// or it's the same as the preference of this customer
					return paint;
				}else if(finalPaints.containsKey(color) ){
					return new Paint(color,Finish.MATTE);
				}
				else {
					// there is another finish needed for this color already,
					// there is no solution.
					return null;
				}
			}
			else {
				if (fixedFinish == null) {
					// 'color' is not in the fixed finishes yet, 
					// let's remember it as a candidate
					paintCandidates.add(paint);
				}
				else if (fixedFinish.equals(finish)){
					// we found one of the paint preferences of this customer
					// in the already fixed paints, let's return it!
					return paint;
				}   
			}
		}

		// for a customer with multiple paint preferences, there were no match 
		// in the already fixed paints; that's not a problem.
		return null;
	}


	private static String createOutput(Map<Integer, Finish> fixedFinishes, int numOfColors) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <=numOfColors; i++) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			Finish finish = fixedFinishes.get(i);
			if (finish == null) {
				// If no specific need for a finish, let's make it gloss, 
				// because it's cheaper. This can happen if there are 
				// more colors than customers.
				finish = Finish.GLOSS;
			}
			sb.append(finish.getCode());
		}
		return sb.toString();
	}

	public static List<String> loadFileToList(String filePath){

		Stream<String> lines;
		List<String> list=null;
		try {
			lines = Files.lines(Paths.get(filePath));
			list = lines.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return list;
	}

	public static Customer inputToCustomerPreferedColors(String preference) throws Exception{

		Customer customer = new Customer();
		char [] prefencesCharArray=preference.toCharArray();
		int color = 0;
		Finish finish = null;
		Paint paint=null;
		for(int i = 0 ;i< prefencesCharArray.length  ; i+=2){
			color=Character.getNumericValue(prefencesCharArray[i]);
			finish= Finish.setCode(String.valueOf(prefencesCharArray[i+1]));
			paint= new Paint(color,finish);
			customer.setPreferedPaints(paint);

		}

		return customer;

	}

	public static void rearangePaintOrder(List <Customer> customers){

		int color=0;
		for(int i =0 ; i<customers.size();i++){
			if(customers.get(i).getPreferedPaints().size()==1){
				color=customers.get(i).getPreferedPaints().get(0).getColor() ;
			}
		}
		for(int i=0;i<customers.size();i++){
			if(color== customers.get(i).getPreferedPaints().get(customers.get(i).getPreferedPaints().size()-1).getColor()){
				Paint temp=customers.get(i).getPreferedPaints().get(customers.get(i).getPreferedPaints().size()-1);
				Paint temp1=customers.get(i).getPreferedPaints().get(0);

				customers.get(i).getPreferedPaints().set(0, temp);
				customers.get(i).getPreferedPaints().set(customers.get(i).getPreferedPaints().size()-1, temp1);
			}
		}
	}


	public static int getDistincPaint(List <Customer> customers){
		int distinctColorNumber=0;
		Map<Integer, Finish> distinctPaint= new HashMap<>();
		for(Customer customer : customers){
			for(int i=0 ;i< customer.getPreferedPaints().size();i++){
				if(!(distinctPaint.containsKey(customer.getPreferedPaints().get(i).getColor()))){
					distinctPaint.put(customer.getPreferedPaints().get(i).getColor(), customer.getPreferedPaints().get(i).getFinish() );
				}

			}
		}
		distinctColorNumber=distinctPaint.size();
		return distinctColorNumber;

	}
}


