import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;

public class patch {
	
	public static void main(String[] args) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] originalHash = md.digest(args[1].getBytes("UTF-8"));	
			byte[] newHash = md.digest(args[2].getBytes("UTF-8"));
			String oldPass = new String(getHex(originalHash));
			String newPass = new String(getHex(newHash));
			
//			RandomAccessFile f = new RandomAccessFile(new File(args[0]), "rw");
//			System.out.println(f.length());
//			f.seek(151598); // Seek ahead
//			f.write(hash);
//			System.out.println(f.length());
//			f.close();
			
			File file = new File(args[0]);
			FileInputStream fIn = new FileInputStream(file);
			fIn = new FileInputStream(file);
			byte fileContent[] = new byte[(int)file.length()];
			// Reads up to certain bytes of data from this input stream into an array of bytes.
			fIn.read(fileContent);
			//create string from byte array
			String fileString = new String(getHex(fileContent));
			
			int index = fileString.indexOf(oldPass);
			
			String newfileString = fileString.replaceAll(oldPass, newPass);
			
			FileOutputStream fileToWrite = new FileOutputStream(file, false);
			fileToWrite.write(hexStringToByteArray(newfileString));
			fileToWrite.close();
			
			System.out.println("Password successfully changed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	public static String getHex( byte [] raw ) {
		String HEXES = "0123456789ABCDEF";
		if ( raw == null ) {
			return null;
		}
		final StringBuilder hex = new StringBuilder( 2 * raw.length );
		for ( final byte b : raw ) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4))
			.append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
}