import java.io.*;
import java.util.*;

public class Anagrams {
	
	public static int size;
	public static ArrayList<ArrayList<String>> sort;

	public static void main(String []args) throws IOException{
		//starts a scanner for the file
		Scanner filename = new Scanner(System.in);
		System.out.println("Please enter file location(Example: src/dict1.txt): ");
		//user inputs the location with name
		String file = filename.nextLine();
		System.out.println("Please enter the name you want the output to be called (ex: anagram1): ");
		String output = filename.nextLine();
		filename.close();
		long start = System.currentTimeMillis();
		//launches readDictionary to read the whole file
		ArrayList<String> unsorted = readDictionary(file);
		sort = sorted(unsorted);
		size = sort.size();
		sortItList(sort);
		long end = System.currentTimeMillis();
		System.out.println("Time(ms): " + (end-start));
		//sorts the dictionary alphabetically
		printToFile(output);
	}
	
	//standard printing to file method using a printwriter
	public static void printToFile(String filename) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		String previous = "";
	    for(int j = 0; j < size; j++){
	    	if(previous.equals(sort.get(j).get(0))){
	    		if(sort.get(j).get(1).length() > 0){
	    			writer.print(" " + sort.get(j).get(1).trim());
	    		}
	    	}
	    	else{
	    		if(!(sort.get(j).get(1).isEmpty())){
	    			if(previous.isEmpty()){
	    				writer.print(sort.get(j).get(1));
	    			} else {
			    		writer.println();
		    			writer.print(" " + sort.get(j).get(1).trim());
	    			}
	    		}
	    	}
	    	previous = sort.get(j).get(0);
	    }
	    writer.close();
	}
	
	//standard bufferedreader class to read from a file
	public static ArrayList<String> readDictionary(String fileName) throws IOException{
		BufferedReader readDictionary = new BufferedReader(new FileReader(fileName));
		ArrayList<String> library = new ArrayList<String>();
		String item;
		//populates the array
		while((item = readDictionary.readLine()) != null){
			if(!(item.isEmpty())){
				library.add(item);
			}
		}
		readDictionary.close();
		return library;
	}
	
	public static ArrayList<ArrayList<String>> sorted(ArrayList<String> library){
		//goes through each ArrayList of strings and populates one of the two slots in the ArrayList<String>
		//with a alphabetized version of the word and one slot without (to make sure we know how to go back from alphabetization)
		int length = library.size();
		ArrayList<ArrayList<String>> sorted = new ArrayList<ArrayList<String>>();
		ArrayList<String> hold;
		//loops through the library passed and assigns sorted and unsorted words to the ArrayList
		for(int j = 0; j < length; j++){
			hold = new ArrayList<String>();
			//calls a method to sort the specific word we are on
			hold.add(sortIt(library.get(j)));
			hold.add(library.get(j));
			sorted.add(hold);
		}
		//returns the sorted word
		return sorted;
	}
	
	
	//this method is used to sort individual strings in alphabetical order
	//unlike the list version of the method, this method actually returns the 
	//new word
	public static String sortIt(String library){
		//converts the string to a char array 
		char[] letterArray = library.toCharArray();
		//uses quicksort learned from class
		int length = letterArray.length;
		quickSort(letterArray, 0, length);
		//creates an empty string to populate
		String sortedString = "";
		//populates the new string with the sorted characters
		for(int j = 0; j < length; j++){
			sortedString = sortedString + letterArray[j];
		}
		//returns the sorted word
		return sortedString;
	}
	
	//This version of sortit just manipulates the passed library
	//and moves around the alphabetized and original word together
	//in a pair which helps keep track of it
	public static void sortItList(ArrayList<ArrayList<String>> library){
		//uses quicksort learned from class
		int length = library.size();
		quickSortList(library, 0, length);
	}
	
	//This is the standard quickSort method I used from the book
	//modified to start at index 0
	public static void quickSort(char[] a, int p, int r){
		if(p < r){
			//q is the pivot, and the two quicksorts below just sort around the pivot
			//eventually it recurses to 1 element comparisons and back up to a sorted string
			int q = partition(a,p,r);
			quickSort(a,p,q);
			quickSort(a,q+1,r);
		}
	}
	
	//this properly compares two characters together
	//and exchanges them according to their size
	//again this method was taken from the book
	public static int partition(char[] a, int p, int r){
		//partition method learned from class modified to start at index 0
		int i = p - 1;
		for(int j = p; j <= r-1; j++){
			if(a[j] < a[r-1]){
				i = i + 1;
				exchange(a,i,j);
			}
		}
		exchange(a,i+1,r-1);
		return i + 1;
	}
	
	//helper method to exchange two characters in an array
	public static char[] exchange(char[] a, int i, int j){
		//just a helper exchange method
		char temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		return a;
	}
	
	//PART 2
	
	//this version of quicksort instead sorts the alphabetized strings in alphabetical order
	//not much is different in the quicksort portion, but partionlist and exchange have been edited
	public static void quickSortList(ArrayList<ArrayList<String>> a, int p, int r){
		//quicksort method from class modified to start at index 0
		if(p < r){
			int q = partitionList(a,p,r);
			quickSortList(a,p,q);
			quickSortList(a,q+1,r);
		}
	}
	
	//this partitions the list and then compares whole words to see which is larger
	//same concept as from the book, except instead of comparing single numbers
	//we are using a compareTo method
	public static int partitionList(ArrayList<ArrayList<String>> a, int p, int r){
		//partition method learned from class modified to start at index 0
		int i = p - 1;
		for(int j = p; j <= r-1; j++){
			if(a.get(j).get(0).compareTo(a.get(r-1).get(0)) < 0){
				i = i + 1;
				exchangeList(a,i,j);
			}
		}
		exchangeList(a,i+1,r-1);
		return i + 1;
	}
	
	//again this is a modified version of the previous exchange
	//this version exchanges the ArrayLists which contain 2 elements, the alphabetized
	//and the non alphabetized version. This way its easier to keep them linked
	public static void exchangeList(ArrayList<ArrayList<String>> a, int i, int j){
		//just a helper exchange method
		ArrayList<String> temp = a.get(i);
		a.set(i, a.get(j));
		a.set(j, temp);
	}
}