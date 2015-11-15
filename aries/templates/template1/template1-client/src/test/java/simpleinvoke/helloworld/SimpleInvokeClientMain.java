package simpleinvoke.helloworld;



public class SimpleInvokeClientMain {

	public static void main(String[] args) {
		try {
			String request = "TOM";
			SimpleInvokeProxy client = new SimpleInvokeProxy();
			String response = client.sayHelloTo(request);
			System.out.println("Response: "+response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
