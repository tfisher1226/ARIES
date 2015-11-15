package simpleinvoke.helloworld;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "HelloWorldWSService", targetNamespace = "http://www.aries.org/simple_invoke/helloworld", wsdlLocation = "http://localhost:8080/SimpleInvoke/HelloWorld?wsdl")
public class HelloWorldWSService extends Service {

    private final static URL HELLOWORLDIMPLSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/SimpleInvoke/HelloWorld?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HELLOWORLDIMPLSERVICE_WSDL_LOCATION = url;
    }

    public HelloWorldWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloWorldWSService() {
        super(HELLOWORLDIMPLSERVICE_WSDL_LOCATION, new QName("http://www.aries.org/simple_invoke/helloworld", "HelloWorldWSService"));
    }

    /**
     * 
     * @return
     *     returns HelloWorld
     */
    @WebEndpoint(name = "HelloWorldWSPort")
    public HelloWorldWS getHelloWorldWSPort() {
        return (HelloWorldWS) super.getPort(new QName("http://www.aries.org/simple_invoke/helloworld", "HelloWorldWSPort"), HelloWorldWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HelloWorld
     */
    @WebEndpoint(name = "HelloWorldWSPort")
    public HelloWorldWS getHelloWorldWSPort(WebServiceFeature... features) {
        return (HelloWorldWS) super.getPort(new QName("http://ws.mkyong.com/", "HelloWorldWSPort"), HelloWorldWS.class, features);
    }
}
