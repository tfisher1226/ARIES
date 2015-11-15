package simpleinvoke.helloworld;



public class HelloWorldWSClientMain {

	public static void main(String[] args) {
		try {
			SayHello request = new SayHello();
			request.setToWhom("TOM");
			HelloWorldWSProxy client = new HelloWorldWSProxy();
			SayHelloResponse response = client.sayHello(request);
			System.out.println("Response: "+response.getReply());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
