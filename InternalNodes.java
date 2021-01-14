package test;

import java.util.Arrays;

public class InternalNodes {
    public static int count(int[] tree) {
    	Arrays.sort(tree);
        int internalCount=0;
        for(int i=0;i<tree.length;i++){
            if(isInTree(i,tree)){
                internalCount++;
            }
        }
        return internalCount;
    }
    private static boolean isInTree(int node,int[] tree){
        return Arrays.binarySearch(tree, node) >= 0;  
//        for(int i=0;i<tree.length;i++){
//             if(node==tree[i]){
//                 return true;
//             }
//        }
//        return false;
    }

    public static void main(String[] args) {
        System.out.println(InternalNodes.count(new int[] { 1, 3, 1, -1, 3 }));
    }
}