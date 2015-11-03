/**
 * autor Lucas
 */
package de.dhbw_mannheim.Better_DH;
import java.io.File; 
import java.io.IOException; 

public class Verzeichniserstellen {

    public static void main(String[] args) throws IOException { 
    }
    
    
   public void Ordnererstellen(){
	   String s = System.getProperty("user.name");
	   //holt sich den Namen des Benutzers auf dem PC
	   String k = "C:/Users/"+s+"/Better-DH";
	   // k = Path zu C:/users/Name/XML
	   if (checkDir(k))
	   //prüft ob es schon vorhanden ist
	   {
	     }
	   else
	   {
		   String path = "C:/Users/"+s+"/"; 
	        String dirName = "Better-DH"; 
	        File dir = new File(path + dirName); 
	        //wenn nicht vorhanden -> lege neuen Ordner in C:/users/name an, der Better-DH hei0t

	        if (dir.mkdir()) { 
	            System.out.println("Ordner erstellt"); 
	        } else { 
	            System.out.println(dir + " konnte nicht erstellt werden"); 
	        } 
	   }
       
    } 
   
   private boolean checkDir(String dirName)
   {
       File stats = new File(dirName);
       if (stats.exists())    
    	   // Überprüfen, ob es den Ordner gibt
       {
           return true;
       }
       else
       {
           if (stats.mkdir())    
        	// Erstellen des Ordners
           {
               return true;
           }
           else
           {
               return false;
           }
       }
   }
} 