public class Main
{
    public static void main (String [] args)
    {
        int i,j;
        int table[][] = {{2,3,4}, {1,2,3}};
        System.out.println(table.length);
        for (i=0; i<table.length; i++)
        {
            System.out.println("wymiar table" + i + "= " + table[i].length);
            for (j=0; j<table[i].length; j++)
            {
                System.out.println(table[i][j]);
            }
        }

    }
}
