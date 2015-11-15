package sample.restaurant;



public class RestaurantMonitor {

    public static RestaurantMonitor INSTANCE = new RestaurantMonitor();

    public static RestaurantMonitor getSingletonInstance() {
        return INSTANCE;
    }


    private RestaurantManager restaurantManager = RestaurantManager.INSTANCE;
    
    public RestaurantMonitor() {
        initialize();
        updateFields();
    }

    private void initialize() {
        //setTitle("Restaurant Service");
        //setName("restaurantView");
    }

    public void reset() {
    	restaurantManager.reset();
        updateFields();
    }

    public void cancel() {
        if (restaurantManager.getIsPreparationWaiting()) {
            Object preparation = restaurantManager.getPreparation();
            try {
                restaurantManager.forceAbort();
                synchronized (preparation) {
                    preparation.notify();
                }
            } catch (Exception e) {
                System.err.println("RestaurantView.jButtonCancelActionPerformed(): Unable to notify preparation.");
            }
        }
    }

    public void confirm() {
        if (restaurantManager.getIsPreparationWaiting()) {
            Object preparation = restaurantManager.getPreparation();
            try {
                restaurantManager.forceCommit();
                synchronized (preparation) {
                    preparation.notify();
                }
            } catch (Exception e) {
                System.err.println("RestaurantView.jButtonCancelActionPerformed(): Unable to notify preparation.");
            }
        }
    }

    public void toggleAutoCommit() {
        if (restaurantManager.isAutoCommitMode()) {
            restaurantManager.setAutoCommitMode(false);
        } else {
            restaurantManager.setAutoCommitMode(true);
        }
        updateFields();
    }

    public void setNTotalSeats(int nSeats) {
        restaurantManager.reset();
        //TODO make sure it is more than nFreeSeats
        int nFreeSeats = restaurantManager.getNFreeSeats();
    }

    public void addMessage(String message) {
    	//TODO add persistent log statement here
    	System.out.println(">>>"+message);
    }

    public void addPrepareMessage(String message) {
        //jButtonConfirm.setBackground(java.awt.Color.red);
        //jButtonCancel.setBackground(java.awt.Color.red);
    	//TODO add persistent log statement here
    	System.out.println(">>>"+message);
    }


    public void updateFields() {
//        jLabelNTotalSeats.setText(Integer.toString(restaurantManager.getNTotalSeats()));
//        jTextFieldNewNTotalSeats.setText(Integer.toString(restaurantManager.getNTotalSeats()));
//        jLabelNPreparedSeats.setText(Integer.toString(restaurantManager.getNPreparedSeats()));
//        jLabelNFreeSeats.setText(Integer.toString(restaurantManager.getNFreeSeats()));
//        jLabelNBookedSeats.setText(Integer.toString(restaurantManager.getNBookedSeats()));
//
//        //update fields related to interactive mode.
//        if (restaurantManager.isAutoCommitMode()) {
//            jLabelResponse.setVisible(false);
//            jButtonConfirm.setVisible(false);
//            jButtonCancel.setVisible(false);
//            jLabelDisplayMode.setText("automatic");
//            
//        } else {
//            jLabelResponse.setVisible(true);
//            jButtonConfirm.setVisible(true);
//            jButtonCancel.setVisible(true);
//            jLabelDisplayMode.setText("interactive");
//        }
    }

}
