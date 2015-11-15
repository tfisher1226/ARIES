package org.aries.util.encrypt;


//TODO - unused for now - placeholder
public class EncryptPassword {

	public static void main(String ar[]) throws Exception {
		String clearTextPassword = ar[0];
		//String clearTextPassword=ar[0]+":ManagementRealm:"+ar[1];     //---> This is how JBoss Encrypts password
		String hashedPassword = createPasswordHash("MD5", "hex", null, null, clearTextPassword);
		System.out.println("clearTextPassword: "+clearTextPassword);
		System.out.println("hashedPassword: "+hashedPassword);
	}

	public static String createPasswordHash(String string, String string2, Object object, Object object2, String clearTextPassword) {
		//TODO - leave open for now
		return null; 
	}

}
