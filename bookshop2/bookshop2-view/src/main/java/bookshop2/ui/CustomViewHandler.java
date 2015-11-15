package bookshop2.ui;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.aries.runtime.BeanContext;

import bookshop2.ui2.NavigationDriver;


public class CustomViewHandler extends ViewHandlerWrapper {

    private static Logger LOGGER = Logger.getLogger(CustomViewHandler.class);

    private ViewHandler wrapped;

    private UIViewRoot root;

    private String viewId;

    
    public CustomViewHandler(ViewHandler wrapped) {
        LOGGER.info("CustomViewHandler.CustomViewHandler():wrapped View Handler:"+wrapped.getClass());
        this.wrapped = wrapped;
    }

    @Override
    public void initView(FacesContext context) throws FacesException {
    	NavigationDriver navigationDriver = BeanContext.getFromSession("navigationDriver");
    	if (navigationDriver != null)
    		navigationDriver.clearState();
        super.initView(context);
    }
    
    @Override
    public UIViewRoot restoreView(FacesContext context, String viewId) {
        try {
            LOGGER.info("restoring view : " + viewId);
            //if (root == null || this.viewId == null || !this.viewId.equals(viewId))
            	root = wrapped.restoreView(context, viewId);
            LOGGER.info("restored view : " + viewId);
            this.viewId = viewId;

        } catch (ViewExpiredException e) {
            LOGGER.error("View Expired : " + e.getMessage() + " -> recreating");
            root = wrapped.createView(context, viewId);
        }
        
        //DebugUtil.printTree(context.getViewRoot(), LOGGER, Level.FINEST);
        return root;
    }

    @Override
    public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
    	getWrapped().renderView(context, viewToRender);
    }
    
    @Override
    public ViewHandler getWrapped() {
        return wrapped;
    }
}
