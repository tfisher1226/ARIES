
package simple_invoke.helloworld;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the simple_invoke.helloworld package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SayGoodbyeResponse_QNAME = new QName("http://simple_invoke/helloworld", "sayGoodbyeResponse");
    private final static QName _SayGoodbye_QNAME = new QName("http://simple_invoke/helloworld", "sayGoodbye");
    private final static QName _SayHello_QNAME = new QName("http://simple_invoke/helloworld", "sayHello");
    private final static QName _SayHelloResponse_QNAME = new QName("http://simple_invoke/helloworld", "sayHelloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: simple_invoke.helloworld
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link SayGoodbyeResponse }
     * 
     */
    public SayGoodbyeResponse createSayGoodbyeResponse() {
        return new SayGoodbyeResponse();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link SayGoodbye }
     * 
     */
    public SayGoodbye createSayGoodbye() {
        return new SayGoodbye();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayGoodbyeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://simple_invoke/helloworld", name = "sayGoodbyeResponse")
    public JAXBElement<SayGoodbyeResponse> createSayGoodbyeResponse(SayGoodbyeResponse value) {
        return new JAXBElement<SayGoodbyeResponse>(_SayGoodbyeResponse_QNAME, SayGoodbyeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayGoodbye }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://simple_invoke/helloworld", name = "sayGoodbye")
    public JAXBElement<SayGoodbye> createSayGoodbye(SayGoodbye value) {
        return new JAXBElement<SayGoodbye>(_SayGoodbye_QNAME, SayGoodbye.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://simple_invoke/helloworld", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://simple_invoke/helloworld", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

}
