package org.aries.jms.config;



public class JmsExchangeDescripter extends JmsEndpointDescripter {

    protected JmsConsumerDescripter _consumerDescripter;
    
    protected JmsProducerDescripter _producerDescripter;
    

	public JmsConsumerDescripter getConsumerDescripter() {
		return _consumerDescripter;
	}

    public void setConsumerDescripter(JmsConsumerDescripter value) {
        _consumerDescripter = value;
    }
    
	public JmsProducerDescripter getProducerDescripter() {
		return _producerDescripter;
	}
	
    public void setProducerDescripter(JmsProducerDescripter value) {
        _producerDescripter = value;
    }

}
