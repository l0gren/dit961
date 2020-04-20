import java.util.Comparator;

public class BuyerComparator implements Comparator<Bid> {
    @Override
    public int compare(Bid a, Bid b) {
        if(a.bid > b.bid) {
            return -1;
        } else if(a.bid < b.bid) {
            return 1;
        } else {
            return 0;
        }
    }
}
