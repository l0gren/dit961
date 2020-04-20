
import java.io.*;
import java.util.*;

public class Lab2 {
	public static String pureMain(String[] commands) {
		SellerComparator sellerComparator = new SellerComparator();
		BuyerComparator buyerComparator = new BuyerComparator();
		PriorityQueue<Bid> sellers = new PriorityQueue<>(sellerComparator);
		PriorityQueue<Bid> buyers = new PriorityQueue<>(buyerComparator);

		StringBuilder sb = new StringBuilder();

		for(int line_no=0;line_no<commands.length;line_no++){
			String line = commands[line_no];
			if( line.equals("") )continue;

			String[] parts = line.split("\\s+");
			if( parts.length != 3 && parts.length != 4)
				throw new RuntimeException("line " + line_no + ": " + parts.length + " words");
			String name = parts[0];
			if( name.charAt(0) == '\0' )
				throw new RuntimeException("line " + line_no + ": invalid name");
			String action = parts[1];
			int price;
			try {
				price = Integer.parseInt(parts[2]);
			} catch(NumberFormatException e){
				throw new RuntimeException(
						"line " + line_no + ": invalid price");
			}

			if( action.equals("K") ) {
				// TODO: add new buy bid
			} else if( action.equals("S") ) {
				// TODO: add new sell bid
			} else if( action.equals("NK") ){
				// TODO: update existing buy bid. use parts[3].
			} else if( action.equals("NS") ){
				// TODO: update existing sell bid. use parts[3].
			} else {
				throw new RuntimeException(
						"line " + line_no + ": invalid action");
			}

			if( sellers.size() == 0 || buyers.size() == 0 )continue;
			
			// TODO:
			// compare the bids of highest priority from each of
			// each priority queues.
			// if the lowest seller price is lower than or equal to
			// the highest buyer price, then remove one bid from
			// each priority queue and add a description of the
			// transaction to the output.
		}

		sb.append("Order book:\n");

		sb.append("Sellers: ");
		// TODO: print remaining sellers.
		//       can remove from priority queue until it is empty.

		sb.append("Buyers: ");
		// TODO: print remaining buyers
		//       can remove from priority queue until it is empty.

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		Bid bid1 = new Bid("Ada", 24);
		Bid bid2 = new Bid("Bda", 12);
		Bid bid3 = new Bid("Cda", 25);
		Bid bid4 = new Bid("Dda", 21);
		Bid bid5 = new Bid("Eda", 0);
		Bid bid6 = new Bid("Fda", 24);
		Bid bid7 = new Bid("Gda", 1337);
		BuyerComparator buyerComparator = new BuyerComparator();
		SellerComparator sellerComparator = new SellerComparator();
		PriorityQueue<Bid> buyers = new PriorityQueue<>(buyerComparator);
		buyers.add(bid1);
		buyers.add(bid2);
		buyers.add(bid3);
		buyers.add(bid4);
		buyers.add(bid5);
		buyers.add(bid6);
		buyers.add(bid7);

		int size = buyers.size();
		for(int i = 0; i < size; i++) {
			Bid min = buyers.minimum();
			System.out.println(min.toString());
			buyers.deleteMinimum();
		}

//		final BufferedReader actions;
//		if( args.length != 1 ){
//			actions = new BufferedReader(new InputStreamReader(System.in));
//		} else {
//			actions = new BufferedReader(new FileReader(args[0]));
//		}
//
//		List<String> lines = new LinkedList<String>();
//		while(true){
//			String line = actions.readLine();
//			if( line == null)break;
//			lines.add(line);
//		}
//		actions.close();
//
//		System.out.println(pureMain(lines.toArray(new String[lines.size()])));
	}
}
