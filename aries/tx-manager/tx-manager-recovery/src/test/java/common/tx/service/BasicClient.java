package common.tx.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import org.aries.tx.client.handler.JaxWSHeaderContextProcessor;

import com.arjuna.ats.jta.UserTransaction;
import common.tx.exception.TransactionRolledBackException;

/**
 * A very basic client application that drives the
 * (transactional) Web Services to arrange a night out.
 */
public class BasicClient extends HttpServlet {

    private ServletContext context ;

    /***** RESTAURANT SERVICE *****/

    private static final String RESTAURANT_NS = "http://www.jboss.com/jbosstm/xts/demo/Restaurant" ;
    private static final String RESTAURANT_PREFIX = "restaurant";

    private static final String RESTAURANT_SERVICE_AT = "RestaurantServiceATService";
    private static final QName RESTAURANT_SERVICE_AT_QNAME = new QName(RESTAURANT_NS, RESTAURANT_SERVICE_AT, RESTAURANT_PREFIX);
    
    private static final String RESTAURANT_ENDPOINT_AT = "RestaurantServiceAT";
    private static final QName RESTAURANT_ENDPOINT_AT_QNAME = new QName(RESTAURANT_NS, RESTAURANT_ENDPOINT_AT, RESTAURANT_PREFIX);


    /***** Client Handles for Service Endpoint Ports *****/

    //private IRestaurantServiceAT restaurantAT;

    /***** Endpoint Addresses *****/

    private String restaurantATURL ;

    /***** WSDL file locations *****/

    private final String restaurantATWSDL = "wsdl/RestaurantServiceAT.wsdl";

    private boolean initialised;
    

    public void init(final ServletConfig config) throws ServletException {
        final String baseURL = "http://localhost:8080/xtsdemowebservices/" ;

        restaurantATURL = getURL(config, "restaurantATURL", baseURL + RESTAURANT_SERVICE_AT);
        context = config.getServletContext();
    }

    /**
     * configure the XTS client handler which manages transaction flow for invocations of the services
     *
     * @param bindingProvider
     */
    private void configureClientHandler(BindingProvider bindingProvider)
    {
        Handler handler = new JaxWSHeaderContextProcessor();
        List<Handler> handlers = Collections.singletonList(handler);
        bindingProvider.getBinding().setHandlerChain(handlers);
    }

    /**
     * Initialise if necessary
     */
    private synchronized void initialise() throws ServletException {
        if (!initialised) {
            try {
                //restaurantAT = getService(RESTAURANT_SERVICE_AT_QNAME, RESTAURANT_ENDPOINT_AT_QNAME, restaurantATURL, restaurantATWSDL, IRestaurantServiceAT.class);
                //configureClientHandler((BindingProvider) restaurantAT);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
            initialised = true ;
        }
    }

    /**
     * Simple wrapper to allow our test method to be invoked when
     * running in a servlet container, taking parameters from the
     * request URL and displaying the outcome on the resulting html page.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initialise();

        // get business logic params from the form submission.
        int restaurantSeats = Integer.parseInt(request.getParameter("restaurant"));

        String result = "Transaction finished OK.";
        String txType = request.getParameter("txType");

        try {
            if ("AtomicTransaction".equals(txType)) {
                testAtomicTransaction(restaurantSeats);
            } else {
                result = "Unknown transaction type " + txType;
            }
            
        } catch (TransactionRolledBackException e) {
            result = "Transaction rolled back.";
            System.out.println("Transaction rolled back") ;
            
        } catch (Exception e) {
            result = "Transaction failed! Cause: " + e.toString();
            System.out.println("CLIENT: problem: ");
            e.printStackTrace(System.out);
        }

        request.setAttribute("result", result);
        context.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void testAtomicTransaction(int restaurantSeats) throws Exception {
        System.out.println("CLIENT: obtaining userTransaction...");

        UserTransaction ut = null; //UserTransactionFactory.getUserTransaction();

        System.out.println("CLIENT: starting the transaction...");

        //ut.begin();

        System.out.println("CLIENT: transaction ID= " + ut.toString());

        System.out.println("CLIENT: calling business Web Services...");

        //restaurantAT.bookSeats(restaurantSeats);

        System.out.println("CLIENT: calling commit on the transaction...");

        //ut.commit();

        System.out.println("done.");
        System.out.flush();
    }

    /**
     * @param config The servlet config
     * @param property The property name
     * @param defaultValue The default value.
     * @return The initialisation property value or the default value if not present. 
     */
    private String getURL(final ServletConfig config, final String property, final String defaultValue)
    {
        // allow command line override via system property
        String value = System.getProperty(property);
        if (value == null) {
            value = config.getInitParameter(property) ;
        }
        return (value == null ? defaultValue : value) ;
    }
    
    /**
     * Get an endpoint reference for a service so we can create a JaxWS port for it
     * @param serviceName the QName of the service in question..
     * @param endpointName the QName of the endpoint associated with the service
     * @param address a string representation of the service URL. null is ok if this is a service located in the
     * same app as the client
     * @return a W3CEndpointReference from which the service port can be obtained.
     */
    private <T> T getService(final QName serviceName, final QName endpointName,
                                            final String address, final String wsdlURL, final Class<T> clazz)
            throws MalformedURLException
    {
        URL url = BasicClient.class.getResource("../../../../../../../" + wsdlURL);
        Service service = Service.create(url, serviceName);
        T port = service.getPort(endpointName, clazz);
        BindingProvider bindingProvider = ((BindingProvider) port);
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return port;
    }
}
