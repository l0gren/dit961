
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
				Bid buyer = new Bid(name, price);
				if(sellers.size() > 0) {
					Bid seller = sellers.minimum();
					if(seller.bid <= buyer.bid){
						sb.append(buyer.name + " buys from " + seller.name + " for " + buyer.bid + "kr\n");
						sellers.deleteMinimum();
					} else {
						buyers.add(buyer);
					}
				} else {
					buyers.add(buyer);
				}

			} else if( action.equals("S") ) {
				// TODO: add new sell bid
				Bid seller = new Bid(name, price);
				if(buyers.size() > 0) {
					Bid buyer = buyers.minimum();
					if(seller.bid <= buyer.bid){
						sb.append(buyer.name + " buys from " + seller.name + " for " + buyer.bid + "kr\n");
						buyers.deleteMinimum();
					} else {
						sellers.add(seller);
					}
				} else {
					sellers.add(seller);
				}

			} else if( action.equals("NK") ){
				// TODO: update existing buy bid. use parts[3].
				Bid oldBid = new Bid(name, price);
				Bid newBid = new Bid(name, Integer.parseInt(parts[3]));
				buyers.update(oldBid, newBid);
				if(buyers.size() > 0 && sellers.size() > 0) {
					Bid buyer = buyers.minimum();
					Bid seller = sellers.minimum();
					if(seller.bid <= buyer.bid) {
						sb.append(buyer.name + " buys from " + seller.name + " for " + buyer.bid + "kr\n");
						buyers.deleteMinimum();
						sellers.deleteMinimum();
					}
				}
			} else if( action.equals("NS") ){
				// TODO: update existing sell bid. use parts[3].
				Bid oldBid = new Bid(name, price);
				Bid newBid = new Bid(name, Integer.parseInt(parts[3]));
				sellers.update(oldBid, newBid);
				if(buyers.size() > 0 && sellers.size() > 0) {
					Bid buyer = buyers.minimum();
					Bid seller = sellers.minimum();
					if(seller.bid <= buyer.bid) {
						sb.append(buyer.name + " buys from " + seller.name + " for " + buyer.bid + "kr\n");
						buyers.deleteMinimum();
						sellers.deleteMinimum();
					}
				}
			} else {
				throw new RuntimeException(
						"line " + line_no + ": invalid action");
			}

			if( sellers.size() == 0 || buyers.size() == 0 )continue;

			while(buyers.size() > 0 && sellers.size() > 0 &&
					sellers.minimum().bid <= buyers.minimum().bid) {

				Bid seller = sellers.minimum();
				Bid buyer = buyers.minimum();
				if(seller.bid <= buyer.bid) {
					sb.append(buyer.name + " buys from " + seller.name + " for " + buyer.bid + "kr\n");
					buyers.deleteMinimum();
					sellers.deleteMinimum();
				}

			}
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
		if(sellers.size() > 0) {
			while(sellers.size() > 1) {
				Bid seller = sellers.minimum();
				sb.append(seller.toString() + ", ");
				sellers.deleteMinimum();
			}
			sb.append(sellers.minimum()+"\n");
		}

		sb.append("Buyers: ");
		// TODO: print remaining buyers
		//       can remove from priority queue until it is empty.
		if(buyers.size() > 0){
			while(buyers.size() > 1) {
				Bid buyer = buyers.minimum();
				sb.append(buyer.toString() + ", ");
				buyers.deleteMinimum();
			}
			sb.append(buyers.minimum()+"\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		final BufferedReader actions;
		if( args.length != 1 ){
			actions = new BufferedReader(new InputStreamReader(System.in));
		} else {
			actions = new BufferedReader(new FileReader(args[0]));
		}

		List<String> lines = new LinkedList<String>();
		while(true){
			String line = actions.readLine();
			if( line == null)break;
			lines.add(line);
		}
		actions.close();

		System.out.println(pureMain(lines.toArray(new String[lines.size()])));
	}
}
