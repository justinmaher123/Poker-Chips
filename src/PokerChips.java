import java.io.*;
import java.util.*;

public class PokerChips {
    final static PrintWriter pw = new PrintWriter(System.out);
    final static Scanner sc = new Scanner(System.in);
    int numOfPlayers;
    HashMap<Integer,Integer> money=new HashMap<>();
    String [] names;
    boolean [] turn;
    int table,cnt,call,bider,raise,lastRaise;
    boolean end,isRaise,finish;
    int [] called;
    void setNum(int n){
        numOfPlayers=n;
        names=new String[numOfPlayers];
        turn=new boolean[numOfPlayers];
        called=new int[numOfPlayers];
    }
    void setTurns(){
        for(int i=0;i<numOfPlayers;i++){
            turn[i]=true;
        }
    }
    void setFinish(){
        finish=false;
    }
    boolean getFinish(){
        return finish;
    }
    void setNamesAndBudget(int i,String s,int budget){
        names[i]=s;
        money.put(i,budget);
    }
    void setDefault1(){
        table=0;
        cnt=0;
        end=false;
    }
    void setDefault2(){
        call=bider=raise=lastRaise=0;
        isRaise=false;
        for(int i=0;i<numOfPlayers;i++){
            called[i]=0;
        }
    }
    int getTable(){
        return table;
    }
    void showMoney(int j){
        int a=money.get(j);
        pw.println(names[j]+"'s turn (current money: "+a+")");
        pw.flush();
    }
    void showAllMoney(){
        for(int i=0;i<numOfPlayers;i++){
            int temp=money.get(i);
            pw.print(names[i]+":"+temp+" ");
            pw.flush();
        }
        pw.println();
        pw.flush();
    }
    boolean checkRoundFinished(){
        if(cnt>=numOfPlayers-1){
            return true;
        }
        return false;
    }
    void Winner(int numWinners){
        table/=numWinners;
        for(int i=0;i<numOfPlayers;i++){
            if(turn[i]){
                pw.print(names[i]+"="+i+" ");
                pw.flush();
            }
        }
        pw.println();
        pw.flush();
    }
    void End(){
        end=true;
    }
    void updateWinner(int winner){
        int update = money.get(winner);
        update +=table;
        money.put(winner, update);
    }
    boolean RaiseEnded(int j){
        if(j==bider&&isRaise){
            return true;
        }
        return false;
    }
    int UpdateRaise(){
        isRaise=false;
        return numOfPlayers;
    }
    boolean checkTurn(int j){
        return turn[j];
    }
    boolean checkIsRaised(){
        return isRaise;
    }
    boolean checkZero(int j){
        int get=money.get(j);
        if(get==0){
            return true;
        }
        return false;
    }
    void fold(int j){
        turn[j]=false;
    }
    void raise(int j,int x){
        int update;
        /*pw.println("raise price ");
        pw.flush();*/
        raise = x;
        update = money.get(j);
        update -=raise;
        update+=called[j];
        table+=raise;
        table-=called[j];
        lastRaise=raise;
        money.put(j, update);
        isRaise=true;
        called[j]=raise;
        bider=j;
    }
    void call(int j){
        int update = money.get(j);
        call=raise-called[j];
        if(update-call<0){
            table+=update;
            update=0;
        }
        else{
            update -=call;
            table+=call;
        }
        money.put(j, update);
    }
    void checkOnlyWinner(){
        int cnt=0;
        int index=0;
        for(int k=0;k<numOfPlayers;k++){
            if(turn[k]==true){
                cnt++;
                index=k;
            }
        }
        if(cnt==1){
            int update = money.get(index);
            update +=table;
            money.put(index, update);
            pw.println(names[index]+" won");
            pw.flush();
            end=true;
        }
    }
    int resetForRaise(int j){
        if(j==numOfPlayers&&isRaise){
            return 0;
        }
        return j;
    }
    boolean checkValidRaise(int raise,int j){
        int get= money.get(j);
        get-=raise;
        get+=called[j];
        if(get<0||raise<=lastRaise){
            return false;
        }
        return true;
    }
    void updateCnt(){
        for(int i=0;i<numOfPlayers;i++){
            int get=money.get(i);
            if(get==0||!turn[i])cnt++;
        }
    }
    boolean getEnd(){
        return end;
    }
    void checkIfGameEnded(){
        int cnt=0;
        for(int i=0;i<numOfPlayers;i++){
            int get=money.get(i);
            if(get==0){
                turn[i]=false;
                cnt++;
            }
            else{
                turn[i]=true;
            }
        }
        if(cnt==numOfPlayers-1){
            finish=true;
        }
    }
    public static void main(String[] args) throws IOException {
        // start your code here
        PokerChips pokerChips=new PokerChips();
        pw.println("how many players ");
        pw.flush();
        int n=sc.nextInt();
        pokerChips.setNum(n);
        pokerChips.setTurns();
        for(int i=0;i<n;i++){
            pw.println("Player "+(i+1)+" name");
            pw.flush();
            String s=sc.next();
            pw.println("Player "+(i+1)+" budget");
            pw.flush();
            int temp=sc.nextInt();
            pokerChips.setNamesAndBudget(i,s,temp);
        }
        pokerChips.setFinish();
        while(!pokerChips.getFinish()){
            pokerChips.setDefault1();
            for(int i=0;i<4;i++){
                pokerChips.setDefault2();
                int j=0;
                if(pokerChips.checkRoundFinished()){
                    pw.println("how many players won");
                    pw.flush();
                    int numWinners= sc.nextInt();
                    pokerChips.Winner(numWinners);
                    for(int k=0;k<numWinners;k++){
                        int winner=sc.nextInt();
                        pokerChips.updateWinner(winner);
                    }
                    pokerChips.End();
                    break;
                }
                pw.println("Round "+(i+1)+" (table money: "+ pokerChips.getTable()+")");
                pw.flush();
                pokerChips.showAllMoney();
                while(j<n){
                    if(pokerChips.RaiseEnded(j)){
                        int temp=pokerChips.UpdateRaise();
                        j=temp;
                    }
                    else{
                        if(pokerChips.checkTurn(j)){
                            pokerChips.showMoney(j);
                            if(!pokerChips.checkIsRaised()){
                                pw.println("check=1 bid=2 fold=3");
                                pw.flush();
                            }
                            else{
                                pw.println("call=1 bid=2 fold=3");
                                pw.flush();
                            }
                            int x= sc.nextInt();
                            if(x==3|| pokerChips.checkZero(j)){
                                pokerChips.fold(j);
                            }
                            else if(x==2){
                                pw.println("enter raise price");
                                pw.flush();
                                int raise=sc.nextInt();
                                while (!pokerChips.checkValidRaise(raise,j)){
                                    pw.println("enter valid price");
                                    pw.flush();
                                    raise= sc.nextInt();
                                }
                                pokerChips.raise(j,raise);
                            }
                            else{
                                pokerChips.call(j);
                            }
                        }
                        j++;
                        pokerChips.checkOnlyWinner();
                        if(pokerChips.getEnd()){
                            break;
                        }
                        int temp =pokerChips.resetForRaise(j);
                        j=temp;
                    }
                }
                pokerChips.updateCnt();
                if(pokerChips.getEnd()){
                    break;
                }
                if(i==3){
                    pw.println("how many players won");
                    pw.flush();
                    int numWinners= sc.nextInt();
                    pokerChips.Winner(numWinners);
                    for(int k=0;k<numWinners;k++){
                        int winner=sc.nextInt();
                        pokerChips.updateWinner(winner);
                    }
                    pokerChips.End();
                    break;
                }
            }
            pokerChips.checkIfGameEnded();
        }
        pokerChips.showAllMoney();
        pw.close();
    }

    static class Scanner {

        StringTokenizer st;
        BufferedReader br;
        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }
        public Scanner(String file) throws IOException {
            br = new BufferedReader(new FileReader(file));
        }
        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }
        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
        public String readAllLines(BufferedReader reader) throws IOException {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            return content.toString();
        }
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        public String nextLine() throws IOException {
            return br.readLine();
        }
        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }
        public long[] nextlongArray(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
        public Long[] nextLongArray(int n) throws IOException {
            Long[] a = new Long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
        public int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }
        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }
        public boolean ready() throws IOException {
            return br.ready();
        }

    }
}
