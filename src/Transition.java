import java.util.Iterator;

/**
 * Created by chenhao on 11/13/15.
 */
public class Transition {

    protected ReceiptCatalog catalog;

    protected ClerkList clerkList;

    protected ProductList productList;

    Transition(){
        catalog = new ReceiptCatalog();
        clerkList=new ClerkList();
        productList=new ProductList();
    }

    Transition(ReceiptCatalog rc, ClerkList clerkList,ProductList productList){
        this.catalog=rc;
        this.clerkList=clerkList;
        this.productList=productList;
        System.out.println("Transition Construct complete ");
    }


    //    return ReceiptList of all record of certain clerkName in certain month
    ReceiptCatalog getClerkMonthRecord(int month, String clerkName){

        //initialise
        ReceiptCatalog monthReceipt=new ReceiptCatalog();

        Iterator iterator =catalog.iterator();
        Receipt r;

        while(iterator.hasNext()){
            r=(Receipt)iterator.next();

            System.out.println(r.clerk.getName()+" "+r.date.getMonth());

            if(r.clerk.getName().equals(clerkName)&&r.date.getMonth()==month){
                monthReceipt.add(r);
            }
        }

        System.out.println(" getClerkMonthRecord complete ");

        return monthReceipt;
    }

    //    calculate certain product total sell amount by Id in the Receipt List rc
    int getProAmount(int proId,ReceiptCatalog rc)
    {
        int amount=0;

        Iterator iter=rc.iterator();
        while(iter.hasNext()){
            Receipt r=(Receipt)iter.next();
            amount+=r.getAmountById(proId);
        }

        System.out.println("getProAmount result: " + amount );
        return amount;

    }

    //    get each clerkName total sale product amount every month
    int getClerkTotalProAmountPerMonth(int month, String clerkName){

        int total=0;

        ReceiptCatalog temp=getClerkMonthRecord(month,clerkName);

        Iterator iterator=temp.iterator();
        while(iterator.hasNext()){
            Receipt r=(Receipt) iterator.next();
            total+=r.getTotalProAmountPerReceipt();
        }

        System.out.println("getClerkTotalProAmountPerMonth result: "+total);

        return total;
    }

    int getProPriceById(int proId){
        int price=0;
        Iterator iterator=productList.iterator();
        while(iterator.hasNext()){
            Product product=(Product)iterator.next();
            if(product.getId()==proId){
                price=product.getPrice();
                break;
            }
        }
        return price;
    }

    int getClerkSaleAmountPerMonth(int month, String clerkName){
        int saleAmount=0;

        ReceiptCatalog mounthRecord=getClerkMonthRecord(month,clerkName);
        Iterator iterator=productList.iterator();

        while(iterator.hasNext()){
            Product product=(Product)iterator.next();
            saleAmount+=getProAmount(product.getId(),mounthRecord)*getProPriceById(product.getId());
        }

        System.out.println("getClerkSaleAmountPerMonth: "+saleAmount);
        return saleAmount;
    }

}
