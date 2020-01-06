package chap14;

public class PersistentTree {
    
    public static void main(String[] args){
    	Tree t=new Tree(
    	        "Mary",22,
                new Tree("Emily",20,
                        new Tree("Alan",50,null,null),
                        new Tree("Georgie",23,null,null)
                ),
                new Tree("Tian",29,
                        new Tree("Raoul",23,null,null),
                        null
                )
        );
    	
    	System.out.println(lookup("Raoul",-1,t));
    	System.out.println(lookup("Jeff",-1,t));

    	Tree f=fupdate("Jeff",80,t);
    	System.out.println(lookup("Jeff",-1,f));
    	
    	Tree u=update("Jim",40,t);
    	System.out.println(lookup("Jeff",-1,u));
    	System.out.println(lookup("Jim",-1,u));

    	Tree f2=fupdate("Jeff",80,t);
    	System.out.println(lookup("Jeff",-1,f2));
    	System.out.println(lookup("Jim",-1,f2));
    	
    }
    
    static class Tree{
        private String key;
        private int val;
        private Tree left,right;

        public Tree(String key, int val, Tree left, Tree right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    public static int lookup(String k,int defaultVal,Tree t){
        if(t==null)
            return defaultVal;
        if(k.equals(t.key))
            return t.val;
        return lookup(k,defaultVal,k.compareTo(t.key)<0 ? t.left : t.right);
    }
    
    public static Tree update(String k,int newVal,Tree t){
        if(t==null)
            t=new Tree(k,newVal,null,null);
        else if(k.equals(t.key))
            t.val=newVal;
        else if(k.compareTo(t.key)<0)
            t.left=update(k,newVal,t.left);
        else
            t.right=update(k,newVal,t.right);
        return t;
    }
    
    public static Tree fupdate(String k,int newVal,Tree t){
        return (t==null)?
                new Tree(k,newVal,null,null):
                k.equals(t.key)?
                        new Tree(k,newVal,t.left,t.right):
                        k.compareTo(t.key)<0?
                                new Tree(t.key,t.val,fupdate(k,newVal,t.left),t.right):
                                new Tree(t.key,t.val,t.left,fupdate(k,newVal,t.right));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
