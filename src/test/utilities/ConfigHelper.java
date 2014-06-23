package test.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import java.util.StringTokenizer;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;
public class ConfigHelper
{
	public String configFile;
	public String baseurl;
	public String username;
	public String password;
	
	public ConfigHelper(String cf) {

		this.configFile = cf;
		
	}
	
	public int getMaxRunCount(){
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(configFile));

        } catch (FileNotFoundException e) {
            System.out.println("1-FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("2-IOException");
            e.printStackTrace();
        } 
        return Integer.parseInt(pro.getProperty("maxRetryCount"));
	}  
	
	public void ReadPro(){
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(configFile));

        } catch (FileNotFoundException e) {
            System.out.println("1-FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("2-IOException");
            e.printStackTrace();
        } 
        baseurl = pro.getProperty("baseurl");
        username = pro.getProperty("username");
        password = pro.getProperty("password");
        System.out.println(baseurl+username+password);
	}  
	public void ReadCsv() {
		try{
        	File csv = new File(".\\conf\\config.csv");
        	BufferedReader br = new BufferedReader(new FileReader(csv));
        	String line = "";
        	while(line==br.readLine()){
        		StringTokenizer st = new StringTokenizer(line,",");
        		while(st.hasMoreTokens()){
        			System.out.println(st.nextToken()+"/t");
        		}
        		System.out.println();
        	}
        	br.close();
        }catch (FileNotFoundException e) {
        	e.printStackTrace();
        }catch(IOException e){
        	e.printStackTrace();
        }
	}
        
        
        
        
    }


