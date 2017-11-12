package requirement0;

import api.ripley.Ripley;


public class Contributors {
	
	public static void main(String[] args) {
		
		System.out.println("Paul Muresan");
		System.out.println("David Asunmo");
		System.out.println("Saadman Sakib");
		System.out.println("Jack Hinshelwood");
		
		Ripley ripley = new Ripley("10tLI3CXstqyVD6ql2OMtA==", "tBgm4pRp9grVqL46EnH7ew==");

		System.out.println(ripley.getLastUpdated());	

	}

}
