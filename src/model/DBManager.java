package model;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;
import java.util.ArrayList;
import java.util.*;


// Data Base Manager of the xml file
public class DBManager {

	// Data Members
	private static final String usersDBPath = "resources/usersDB/usersDB.xml"; 
	
	// Getter
	public static String getUsersDBPath() {
		return usersDBPath;
	}
	

	synchronized public static Element getRootOfXmlFile (String filePath) {
		try {
			// Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file and obtain the Document object
            Document document = builder.parse(filePath);

            // Get the root element
            return document.getDocumentElement();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
	
	synchronized public static Document getdocumentOfXmlFile (String filePath) {
		try {
			// Create a DocumentBuilderFactory
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	        // Create a DocumentBuilder
	        DocumentBuilder builder = factory.newDocumentBuilder();
	
	        // Parse the XML file and obtain the Document object
	        return builder.parse(new File(usersDBPath));

        } catch (Exception e) {
            return null;
        }

	}
	
	synchronized public static Boolean getUserconnected(String userName) {	
		try {
			// Get the root element (users)
			Element root = getRootOfXmlFile(getUsersDBPath());
			NodeList userNodes = root.getElementsByTagName("user");

	        // Process each user element
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);

                // Check if the name attribute matches the User object's name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // User exists in the XML file
                    String inGame =  userElement.getAttribute("connected");
                    if (inGame.equals("true")) {
                    	return true;
                    }
                    else {
                    	return false;
                    }
                }
	        }
	        
	        return false;
	        
		} catch (Exception e) {
	        e.printStackTrace();
	        // User not found or xml file not opened
	        return false;
	    }
		
	}

	synchronized public static String getUserPassword(String userName) {
	
			try {
				// Get the root element (users)
				Element root = getRootOfXmlFile(getUsersDBPath());
				NodeList userNodes = root.getElementsByTagName("user");
	
		        // Process each user element
		        for (int i = 0; i < userNodes.getLength(); i++) {
		        	
		        	Element userElement = (Element) userNodes.item(i);
	
	                // Check if the name attribute matches the User object's name
	                String name = userElement.getAttribute("name");
	                if (userName.equals(name)) {
	                    // User exists in the XML file
	                    return userElement.getAttribute("password");
	                }
		        }
		        
		        return null;
		        
			} catch (Exception e) {
		        e.printStackTrace();
		        // User not found or xml file not opened
		        return null;
		    }
	    }
	
	
	synchronized public static String getUserTotalAmount(String userName) {
		try {
			// Get the root element (users)
			Element root = getRootOfXmlFile(getUsersDBPath());
			NodeList userNodes = root.getElementsByTagName("user");

	        // Process each user element
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);

                // Check if the name attribute matches the User object's name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // User exists in the XML file
                	return userElement.getAttribute("total_amount");
	            }
	        }
	        
	        return null;
	        
		} catch (Exception e) {
	        e.printStackTrace();
	        // User not found or xml file not opened
	        return null;
	    }
	}
	
	
	synchronized public static String getUserNumberOfWin(String userName) {
		try {
			// Get the root element (users)
			Element root = getRootOfXmlFile(getUsersDBPath());
			NodeList userNodes = root.getElementsByTagName("user");

	        // Process each user element
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);

                // Check if the name attribute matches the User object's name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // User exists in the XML file
                	return userElement.getAttribute("number_of_wins");
	            }
	        }
	        
	        return null;
	        
		} catch (Exception e) {
	        e.printStackTrace();
	        // User not found or xml file not opened
	        return null;
	    }
	}
	
	
	synchronized public static String getUserTotalProfit(String userName) {
		try {
			// Get the root element (users)
			Element root = getRootOfXmlFile(getUsersDBPath());
			NodeList userNodes = root.getElementsByTagName("user");

	        // Process each user element
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);

                // Check if the name attribute matches the User object's name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // User exists in the XML file
                	return userElement.getAttribute("total_profit");
	            }
	        }
	        
	        return null;
	        
		} catch (Exception e) {
	        e.printStackTrace();
	        // User not found or xml file not opened
	        return null;
	    }
	}
    
    
	synchronized public static Boolean setUserPassword(String userName, String newPassword) {
		
		try {
			
            // Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // Set the new password attribute
                    userElement.setAttribute("password", newPassword);

                    // Save the modified DOM structure back to the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(usersDBPath));
                    transformer.transform(source, result);

                    return true; // Password updated successfully
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }
	}
	
	
	synchronized public static Boolean setUserTotalAmount(String userName, String totalAmount) {
		try {
			
            // Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // Set the new total_amount attribute
                    userElement.setAttribute("total_amount", totalAmount);

                    // Save the modified DOM structure back to the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(usersDBPath));
                    transformer.transform(source, result);

                    return true; // Patotal_amountssword updated successfully
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }

	}
	
	
	synchronized public static Boolean setUserNumberOfWins(String userName, String numberOfWins) {
		try {
			
            // Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // Set the new number_of_wins attribute
                    userElement.setAttribute("number_of_wins", numberOfWins);

                    // Save the modified DOM structure back to the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(usersDBPath));
                    transformer.transform(source, result);

                    return true; // number_of_wins updated successfully
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }   	

	}
	
	
	synchronized public static Boolean setTotalProfitToUser(String userName, String totalProfit) {
		try {
			
            // Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // Set the new total_profit attribute
                    userElement.setAttribute("total_profit", totalProfit);

                    // Save the modified DOM structure back to the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(usersDBPath));
                    transformer.transform(source, result);

                    return true; // total_profit updated successfully
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }   	

	}

	synchronized public static Boolean setconnectedToUser(String userName, Boolean inGame) {
		try {
			
            // Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    // Set the new total_profit attribute
                	String inGameStr;
                	if (inGame == true) {
                		
                		inGameStr = "true";
                	}
                	else {
                		
                		inGameStr = "false";
                	}
                    userElement.setAttribute("connected", inGameStr);

                    // Save the modified DOM structure back to the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(usersDBPath));
                    transformer.transform(source, result);

                    return true; // total_profit updated successfully
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }   	

	}
	
	synchronized public static Boolean setconnectedToUser(User user, Boolean inGame) {
		
		return setconnectedToUser(user.getName(),inGame);
	}
	
	synchronized public static User addNewUserToDB (String newuserName, String newuserPassword,String newTotalAmount, String newNumberOfWins, String newTotalProfit) {
		try {
			User newUser = addNewUserToDB(newuserName,newuserPassword);
			if (newUser == null) {
				return null;
			}
			newUser.setNumberOfWins(Integer.parseInt(newNumberOfWins));
			newUser.setTotalAmount(Integer.parseInt(newTotalAmount));
			newUser.setTotalProfit(Integer.parseInt(newTotalProfit));
			
			if (updateUserValues(newUser) == true ) {
				
				return newUser;
			}
			return null;
			
        } catch (Exception e) {
            return null;
        }	
	}
	// if return null : user exit already in the DB or can't open xml file
	synchronized public static User addNewUserToDB (String userName, String userPassword) {
        try {
        	
        	if (isUserInDB(userName) == true) {
        		return null;
        	}
        	
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file and obtain the Document object
            Document document = builder.parse(new File(usersDBPath));

            // Create the new user element
            Element userElement = document.createElement("user");
            userElement.setAttribute("name", userName);
            userElement.setAttribute("password", userPassword);
            userElement.setAttribute("total_amount", "0");
            userElement.setAttribute("number_of_wins", "0");
            userElement.setAttribute("total_profit", "0");
            userElement.setAttribute("connected", "false");

            // Get the root element
            Element rootElement = document.getDocumentElement();
            
            rootElement.appendChild(document.createTextNode("\n"));

            // Append the new user element to the root element
            rootElement.appendChild(userElement);

            // Save the modified DOM structure back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(usersDBPath));
            transformer.transform(source, result);

            return getUserFromDB(userName,userPassword); // User added successfully
        } catch (Exception e) {
            return null;
        }		
    }
	
	synchronized public static User getUserFromDB (String userName, String userPassword) {
		try {
			// Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                String password = userElement.getAttribute("password");
                if (userName.equals(name) && password.equals(userPassword)) {
                	
                	int totalPtofit = Integer.parseInt(userElement.getAttribute("total_profit")); 
                	int numOfWins = Integer.parseInt(userElement.getAttribute("number_of_wins"));              
                	int totalAmount = Integer.parseInt(userElement.getAttribute("total_amount"));    
                	User newUser = new User(userName,totalAmount,numOfWins,totalPtofit);
                	
                	return newUser;
                }
	        }
	        return null;
        } catch (Exception e) {
        	return null;
        }
	}
	
	synchronized public static Boolean validUserPassword (String userName, String userPassword) {
		try {
			// Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                String password = userElement.getAttribute("password");
                if (userName.equals(name) && password.equals(userPassword)) {
                    	return true; 
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }
	}
	synchronized public static Boolean validUserPassword (User anyUser, String userPassword) {
		
		return validUserPassword(anyUser.getName(),userPassword);
	}
	synchronized public static Boolean isUserInDB (User anyUser) {
		try {
			// Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                if (anyUser.getName().equals(name)) {
                    	return true; 
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }
	}
	synchronized public static Boolean isUserInDB (String userName) {
		try {
			// Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	        	// Check if the name attribute matches the given user name
                String name = userElement.getAttribute("name");
                if (userName.equals(name)) {
                    	return true; 
                }
	        }
	        return false;
        } catch (Exception e) {
        	return false;
        }
	}
	
	synchronized public static Boolean updateUserValues (User anyUser) {
		
		try {
				String userName = anyUser.getName();
            	String totalProfit = Integer.toString(anyUser.getTotalProfit());
            	String numberOfWins = Integer.toString(anyUser.getNumberOfWins());
            	String totalAmount = Integer.toString(anyUser.getTotalAmount());
            	
            	return (setTotalProfitToUser(userName,totalProfit) &&
            			setUserNumberOfWins(userName,numberOfWins) && 
            		    setUserTotalAmount(userName,totalAmount));
            	
	            	
	         
	     
        } catch (Exception e) {
        	return false;
        }
	}
	
	
	synchronized public static ArrayList<Map<String, String>> getAllUsers () {
	
		try {
			
			ArrayList<Map<String, String>> allUsers = new ArrayList<Map<String, String>>();
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	// add user to list
	        	Element userElement = (Element) userNodes.item(i);
	        	
                String name = userElement.getAttribute("name");
                String numberOfWins = userElement.getAttribute("number_of_wins");
                String totalProfit = userElement.getAttribute("total_profit");
                Map<String, String> userAttributes = new HashMap<>();
                
                userAttributes.put("Name", name);
                userAttributes.put("Number of wins", numberOfWins);
                userAttributes.put("Total profit", totalProfit);
                
                allUsers.add(userAttributes);
	        }
	        
	        return allUsers;
	            	
	    } catch (Exception e) {
	    	return  new ArrayList<Map<String, String>>();
	    }
		
	}
	
	
	
    public static ArrayList<Map<String, String>> sortUsersByWins() {
    	
        ArrayList<Map<String, String>> users = getAllUsers();
        users.sort(new WinsComparator());
        return users;
    }

    public static ArrayList<Map<String, String>> sortUsersByProfit() {
    	
        ArrayList<Map<String, String>> users = getAllUsers();
        users.sort(new ProfitComparator());
        return users;
    }
    
    public static void resetConnectedToFalse() {
    	
    	try {
			
            // Parse the XML file and obtain the Document object
            Document document = getdocumentOfXmlFile(usersDBPath);

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Get the user elements
            NodeList userNodes = rootElement.getElementsByTagName("user");
	        
	        for (int i = 0; i < userNodes.getLength(); i++) {
	        	
	        	Element userElement = (Element) userNodes.item(i);
	  
                    userElement.setAttribute("connected", "false");

                    // Save the modified DOM structure back to the XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(usersDBPath));
                    transformer.transform(source, result);

            }
	        
	  
        } 
    	catch (Exception e) {
        	
        }   	
    	
    }
	
	
}
