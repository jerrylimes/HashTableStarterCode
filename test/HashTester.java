package test;

import hash.Account;
import hash.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class HashTester {
    /**
     * THIS IS A SANITY CHECK. NOT MEANT TO BE A THOROUGH TEST OF YOUR HASH TABLE
     * @author floryan
     *
     * Changes Made by @Hunter Platt to switch from Integers to Account/Person
     */
    public static void main(String[] args) {
            /* Num Tests */
            final int NUM_TESTS = 1000;

            /* Make two hashmaps */
            java.util.HashMap<Account, Person > cmp = new java.util.HashMap<>();
            hash.HashTable<Account, Person> stud = new hash.HashTable<>();

            HashSet<String> people = populatePeople();

            Random random = new Random();

            System.out.print("Inserting...");
            for(String name: people) {
                String username = name.replace(" ","")+random.nextInt(1000);
                String password = generateRandomString();
                Person person = new Person(new Account(username, password), name);

                cmp.put(person.getAccount(), person);
                stud.insert(person.getAccount(), person);
            }
            System.out.println("DONE");

            System.out.print("Checking if inserted items present...");
            for(Account k : cmp.keySet()) {
                if(!cmp.get(k).equals(stud.retrieve(k))) {
                    System.out.println("ERROR: Key " + k + " was inserted but not found OR has incorrect value in your hash table");
                    System.exit(-1);
                }
            }
            System.out.println("DONE");

            System.out.print("Changing Values...");
            for(Account k : cmp.keySet()) {
                Iterator<String> it = people.iterator();
                Person person = new Person(k, it.next());

                cmp.put(k, person);
                stud.insert(k, person);
            }
            System.out.println("Done");

            System.out.print("Checking changes by retrieving items again...");
            for(Account k : cmp.keySet()) {
                if(!cmp.get(k).equals(stud.retrieve(k))) {
                    System.out.println("ERROR: Key " + k + " was inserted and updated but not found OR has incorrect value in your hash table");
                    System.exit(-1);
                }
            }
            System.out.println("DONE");

            /*REMOVE*/
            System.out.print("Removing all items and checking...");
            for(Account k : cmp.keySet()) {
                stud.remove(k);

                if(stud.retrieve(k) != null) {
                    System.out.println("ERROR: Key " + k + " was removed but did not return null upon calling retrieve()");
                }
            }
            System.out.println("DONE");



        }

    private static HashSet<String> populatePeople() {
        ArrayList<String> firstNames = new ArrayList<>();
        firstNames.add("Alice"); firstNames.add("Bob"); firstNames.add("Charlie"); firstNames.add("David"); firstNames.add("Eve"); firstNames.add("Frank");
        firstNames.add("Grace"); firstNames.add("Hannah"); firstNames.add("Isaac"); firstNames.add("Jack"); firstNames.add("Karen"); firstNames.add("Liam");
        firstNames.add("Mia"); firstNames.add("Hunter"); firstNames.add("Olivia"); firstNames.add("Paul"); firstNames.add("Quinn"); firstNames.add("Ruby");
        firstNames.add("Sophia"); firstNames.add("Thomas"); firstNames.add("Uma"); firstNames.add("Victor"); firstNames.add("Wendy"); firstNames.add("Xander");
        firstNames.add("Yara"); firstNames.add("Zachary"); firstNames.add("Bella"); firstNames.add("Carter"); firstNames.add("Diana"); firstNames.add("Elijah");
        firstNames.add("Fiona"); firstNames.add("George"); firstNames.add("Hazel"); firstNames.add("Ian"); firstNames.add("Jade");
        ArrayList<String> lastNames = new ArrayList<>();
        lastNames.add("Smith"); lastNames.add("Johnson"); lastNames.add("Williams"); lastNames.add("Brown"); lastNames.add("Jones"); lastNames.add("Garcia");
        lastNames.add("Miller"); lastNames.add("Davis"); lastNames.add("Rodriguez"); lastNames.add("Martinez"); lastNames.add("Hernandez"); lastNames.add("Lopez");
        lastNames.add("Gonzalez"); lastNames.add("Wilson"); lastNames.add("Anderson"); lastNames.add("Thomas"); lastNames.add("Taylor"); lastNames.add("Moore");
        lastNames.add("Jackson"); lastNames.add("Martin"); lastNames.add("Lee"); lastNames.add("Perez"); lastNames.add("Thompson"); lastNames.add("White");
        lastNames.add("Harris"); lastNames.add("Sanchez"); lastNames.add("Clark"); lastNames.add("Ramirez"); lastNames.add("Lewis"); lastNames.add("Robinson");
        lastNames.add("Walker"); lastNames.add("Young"); lastNames.add("Allen"); lastNames.add("King"); lastNames.add("Platt");

        HashSet<String> fullNames = new HashSet<>();
        Random random = new Random();
        while (fullNames.size()<1000){
            String fullName = firstNames.get(random.nextInt(firstNames.size()))+" "+lastNames.get(random.nextInt(lastNames.size()));
            fullNames.add(fullName);
        }
        System.out.println(fullNames.size());
        return fullNames;
    }

    private static String generateRandomString(){
        // https://www.baeldung.com/java-random-string --> Example 3

            int leftLimit = 97; //"a"
            int rightLimit = 122; //"z"
            int targetLength = (int) (Math.random()*5.0+8);
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetLength);
            for (int i = 0; i<targetLength; i++){
                int randomLimitedInt = random.nextInt(leftLimit,rightLimit+1);
                buffer.append((char) randomLimitedInt);
            }
            return buffer.toString();


        }

    }


