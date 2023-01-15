import java.util.*;
import java.io.*;
import java.util.Scanner;

public class surviv {
	static Scanner sc = new Scanner (System.in);
	public static void main(String[] args)throws Exception{
		//starts program
		initiate();
	}
		public static void initiate()throws Exception{
		cls();
		String userin;
		System.out.println("------------------------------------------\nWelcome to the Surviv.io Simulator!\n(1) Intructions\n(2) Customize\n(3) Use default stats\n------------------------------------------");
		while (true){
			userin = sc.nextLine();
			if (userin.equals("1")){
				instructions();
				break;
			}else if(userin.equals("2")){
				System.out.print("Feature has not been added yet.");
				break;
			}else if (userin.equals("3")){
				Simulation simulation = new Simulation();
				simulation.start();
				break;
			}else{
				System.out.print("Invalid input.");
			}
		}
	}
	public static void instructions()throws Exception{
		cls();
		boolean isright = false;
		String userin = "";
		String instructions = "";
		Scanner scFile = new Scanner(new File("instructions.txt"));

		while (scFile.hasNext()){
			//import instructions file into var inctructions
			instructions = instructions+"\n"+scFile.nextLine();
		}
		System.out.println(instructions);
 
		System.out.println();

		while (!isright){
			System.out.println("Press 1 to go back");
			userin = sc.nextLine();
			if (userin.equals("1")){
				initiate();
				break;
			}else{
				continue;
			}
		}
	}
	public static void cls(String... arg) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}
class Simulation{
	static Scanner sc = new Scanner (System.in);
	String WS1, WS2, WS3, WR1, WR2, WR3, WR4, WR5, WT1, WT2, WT3, WT4, WT5, WT6;
	int counter;
	String fil;
	int day;
	public Simulation (){
		WS1 = "";
		WS2 = "";
		WS3 = "";
		WR1 = "";
		WR2 = "";
		WR3 = "";
		WR4 = "";
		WR5 = "";
		WT1 = "";
		WT2 = "";
		WT3 = "";
		WT4 = "";
		WT5 = "";
		WT6 = "";
		day = 1;
		counter = 0;
		fil = "names.txt";
	}
	public void start()throws Exception{
		Scanner scFile = new Scanner(new File(fil));
		Scanner numoflines = new Scanner(new File(fil));
		while (numoflines.hasNext()){
			numoflines.nextLine();
			counter++;
		}
		Player players [] = new Player[counter];
		for (int i = 0; i < counter; i++){
			Player p = new Player(scFile.nextLine());
			players[i] = p;
		}
		for (int i = 0; i < counter; i++){
			System.out.println(players[i].name+" ("+players[i].skill+") has spawned.");
		}
		WS1 = RFile(WS1, "WeaponsS1");
		WS2 = RFile(WS2, "WeaponsS2");
		WS3 = RFile(WS3, "WeaponsS3");
		WR1 = RFile(WR1, "WeaponsR1");
		WR2 = RFile(WR2, "WeaponsR2");
		WR3 = RFile(WR3, "WeaponsR3");
		WR4 = RFile(WR4, "WeaponsR4");
		WR5 = RFile(WR5, "WeaponsR5");
		WT1 = RFile(WT1, "WeaponsT1");
		WT2 = RFile(WT2, "WeaponsT2");
		WT3 = RFile(WT3, "WeaponsT3");
		WT4 = RFile(WT4, "WeaponsT4");
		WT5 = RFile(WT5, "WeaponsT5");
		WT6 = RFile(WT6, "WeaponsT6");
		System.out.println("Press 'c' to continue");
		String userin = sc.nextLine();
		while(true){
			if (userin.equals("c")){
				for (int i = 0; i < counter; i++){
					System.out.println(players[i].name+" ("+players[i].skill+") has found "+ players[i].weapon+".");
				}
				simulate(counter, players);
				break;
			}else{
				System.out.println("Invalid input.");
				userin = sc.nextLine();
			}
		}
	}
	
	public void simulate(int counter, Player players [])throws Exception{
		boolean winner = false;
		while (!winner){
			System.out.println("-----------------------------------------------");
			System.out.println("Press c to continue.");
			System.out.println("-----------------------------------------------");
			String userin = sc.nextLine();
			while(true){
				if (userin.equals("c")){
					break;
				}else{
					System.out.println("Invalid input.");
					userin = sc.nextLine();
				}
			}
			System.out.println();
			System.out.println();
			System.out.println("-----------------------------------------------");
			System.out.println("Minute "+day);
			System.out.println("-----------------------------------------------");
			for (int i = 0; i < counter; i++){
				if (players[i].name.charAt(0) == '*'){
					continue;
				}
				players[i].battle(counter, players, i);
			}
			printalive(players, counter);
			if (checkwinner(players, counter)){
				winner = true;
				System.out.println();
				System.out.println(printwinner(players, counter));
				System.out.println();
				System.out.println();
				System.out.println("Player Stats:");
				System.out.println("-----------------------------------------------");
				printstats(players, counter);
				System.out.println("-----------------------------------------------");
			}
			day++;
		}
	}
	public void printstats(Player players [], int counter){
		for (int i = 0; i < counter; i++){
			System.out.println(players[i].name+"("+players[i].skill+")"+": "+players[i].kills+" kills");
		}
	}
	
	public String RFile(String s, String n)throws Exception{
		Scanner scFile = new Scanner(new File(n+".txt"));
		while (scFile.hasNext()){
			s = s+"\n"+scFile.nextLine();
		}
		return s;
	}
	public boolean checkwinner(Player players [], int counter){
		int c = 0;
		for (int i = 0; i < counter; i++){
			if (players[i].name.charAt(0) != '*'){
				c ++;
			}
			if (c > 1){
				return false;
			}
		}
		if (c == 1){
			return true;
		}
		return false;
	}
	public String printwinner(Player players [], int counter){
		String s = "";
		for (int i = 0; i < counter; i++){
			if (players[i].name.charAt(0) != '*'){
				s = "The winner is "+players[i].name+" with "+ players[i].kills+" kills!!!";
				break;
			}
		}
		return s;
	}
	public void printalive(Player players [], int counter){
		System.out.println("-----------------------------------------------");
		System.out.println("Players still alive:");
		System.out.println("-----------------------------------------------");
		for (int i = 0; i < counter; i++){
			if (players[i].name.charAt(0) != '*'){
				System.out.println(players[i].name+"("+players[i].skill+")"+" with "+players[i].weapon);
			}
		}
	}
	
	public static void cls(String... arg) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}

class Player{
	String name;
	int skill;
	String weapon;
	int health;
	int damage;
	int kills;
	public Player (String n)throws Exception{
		name = n;
		skill = srand();
		weapon = assignweapon();
		health = 100;
		damage = 0;
		kills = 0;
	}
	public int srand(){
		int r = ((int)(Math.random()*10))+1;
		return r;
	}
	public String assignweapon()throws Exception{
		String weapon;
		int counter = 0;
		int x;
		int r = ((int)(Math.random()*100))+1;
		if (r >=0 && r <= 40){
			x = 1;
		}else if (r >=41 && r <= 70){
			x = 2;
		}else if (r >=71 && r <= 87){
			x = 3;
		}else if (r >=88 && r <= 96){
			x = 4;
		}else{
			x = 5;
		}
		Scanner scFile = new Scanner(new File("WeaponsR"+x+".txt"));
		Scanner numoflines = new Scanner(new File("WeaponsR"+x+".txt"));
		while (numoflines.hasNext()){
			numoflines.nextLine();
			counter++;
		}
		int v = ((int)(Math.random()*counter))+1;
		for (int i = 0; i < v-1; i++){
			scFile.nextLine();
		}
		weapon = scFile.nextLine();
		return weapon;
	}
	public void battle(int counter, Player players [], int q)throws Exception{
		boolean found;
		int r = ((int)(Math.random()*10))+1;
		if (r >= 1 && r <= 7){
			found = true;
		}else{
			found = false;
		}
		if (found){
			r = ((int)(Math.random()*counter));
			//for (int i = 0; i <= counter; i++){
				while (players[r].name.charAt(0) == '*' || r == q){
					r++;
					if (r == counter){
						r = 0;
					}
				}
				/*if (i == counter){
						System.out.println(players[q].name+" did not come across anyone...");
						break;
					}*/
				//if (i == r){
					//if (players[i].name.charAt(0) == '*'){
						//continue;
					//}
					if (winner(players, q, r)){
						System.out.println(players[q].name+"("+players[q].skill+")"+" killed "+players[r].name+"("+players[r].skill+")"+" with "+players[q].weapon+".");
						players[r].name = "*"+players[r].name;
						players[q].kills ++;
						if (switchweapon(players, q, r)){
							players[q].weapon = players[r].weapon;
						}
					}else{
						System.out.println(players[r].name+"("+players[r].skill+")"+" killed "+players[q].name+"("+players[q].skill+")"+" with "+players[r].weapon+".");
						players[q].name = "*"+players[q].name;
						players[r].kills ++;
						if (switchweapon(players, r, q)){
							players[r].weapon = players[q].weapon;
						}
					}
				//}
			//}
		}else{
			System.out.println(players[q].name+" did not come across anyone...");
		}
	}
	public boolean winner (Player players [], int q, int i)throws Exception{
		float winscoreA =  players[q].skill*findWS(players, q)+(findWT(players, q)*2);
		float winscoreB = players[i].skill*findWS(players, i)+(findWT(players, i)*2);
		float multiplyer = 100/(winscoreA+winscoreB);
		int pscore = (int)(winscoreA*multiplyer)+1;
		int r = ((int)(Math.random()*99))+1;
		if (r <= pscore){
			return true;
		}else{
			return false;
		}
	}
	public int findWS(Player players [], int p)throws Exception{
		String w = players[p].weapon;
		for (int i = 1; i <= 3; i++){
			int counter = 0;
			Scanner scFile = new Scanner(new File("WeaponsS"+i+".txt"));
			Scanner numoflines = new Scanner(new File("WeaponsS"+i+".txt"));
			while (numoflines.hasNext()){
				numoflines.nextLine();
				counter++;
			}
			for (int q = 0; q < counter; q++){
				String d = scFile.nextLine();
				if (d.equals(w)){
					return i;
				}else{
					continue;
				}
			}
		}
		return 0;
	}
	public int findWT(Player players [], int p)throws Exception{
		String w = players[p].weapon;
		for (int i = 1; i <= 5; i++){
			int counter = 0;
			Scanner scFile = new Scanner(new File("WeaponsT"+i+".txt"));
			Scanner numoflines = new Scanner(new File("WeaponsT"+i+".txt"));
			while (numoflines.hasNext()){
				numoflines.nextLine();
				counter++;
			}
			for (int q = 0; q < counter; q++){
				String d = scFile.nextLine();
				if (d.equals(w)){
					return i;
				}else{
					continue;
				}
			}
		}
		return 0;
	}
	public boolean switchweapon(Player players[], int winner, int loser)throws Exception{
		int w = (players[winner].skill*findWS(players, winner))+(findWT(players, winner)*2);
		int l = (players[winner].skill*findWS(players, loser))+(findWT(players, loser)*2);
		if (l > w){
			return true;
		}else{
			return false;
		}
	}
}
