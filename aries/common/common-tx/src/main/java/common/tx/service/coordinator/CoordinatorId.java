package common.tx.service.coordinator;


public interface CoordinatorId {

    public boolean equals (Object obj);
    

    public byte[] value ();
    

    public boolean valid ();


    public String toString ();
    
}
