import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lab3 {
    public static void main( String[] args ) {
        Scanner scan = null;
        try {
            scan = new Scanner( new File( "Dict.txt" ) );
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }

        ArrayList<String> dictionary = new ArrayList<>();
        ArrayList<String> anagrams = new ArrayList<>();


        if ( scan != null ) {
            while ( scan.hasNext() ) dictionary.add( scan.next().toLowerCase() );
        }

        String maxWord = dictionary.get( 0 );
        int max = 0;

        long start = System.currentTimeMillis();

        for ( int i = 0; i < dictionary.size(); i++ ) {
            ArrayList<String> list = new ArrayList<>();
            String curWord = dictionary.get( i );
            int count = 0;

            for ( int j = 0; j < dictionary.size(); j++ )
                if ( techniqueOne( curWord, dictionary.get( j ) ) ) {
                    list.add( dictionary.get( j ) );
                    dictionary.remove( j );
                    count++;
                }

            if ( count > max ) {
                max = count;
                maxWord = curWord;
                anagrams.clear();
                anagrams = list;
            }
        }

        scan.close();

        long end = System.currentTimeMillis();
        float totalTime = (float) ( ( end - start ) / 1000.0 );

        System.out.println( "Technique #1: [" + maxWord + "] has " + ( max - 1 ) + " anagrams " + totalTime + " secs" );
        System.out.println( anagrams );

    }

    private static boolean techniqueOne( String wordOne, String wordTwo ) {
        if ( wordOne.length() != wordTwo.length() ) return false;
        for ( int i = 0; i < wordOne.length(); i++ )
            if ( wordTwo.indexOf( wordOne.charAt( i ) ) == -1 ) return false;
            else {
                wordTwo = wordTwo.substring( 0, wordTwo.indexOf( wordOne.charAt( i ) ) )
                        + wordTwo.substring( wordTwo.indexOf( wordOne.charAt( i ) ) + 1 );
            }
        return wordTwo.length() == 0;
    }

    private static boolean techniqueTwo( String wordOne, String wordTwo ) {
        if ( wordOne.length() != wordTwo.length() ) return false;

        char[] wordOneArray = wordOne.toLowerCase().toCharArray();
        char[] wordTwoArray = wordTwo.toLowerCase().toCharArray();
        Arrays.sort( wordOneArray );
        Arrays.sort( wordTwoArray );

        return Arrays.equals( wordOneArray, wordTwoArray );
    }

    private static boolean techniqueThree( String wordOne, String wordTwo ) {
        if ( wordOne.length() != wordTwo.length() ) return false;

        int[] letters = new int[ 128 ];

        for ( int i = 0; i < wordOne.length(); i++ ) {
            letters[ wordOne.charAt( i ) ]++;
            letters[ wordTwo.charAt( i ) ]--;
        }

        for ( int letter : letters ) if ( letter != 0 ) return false;
        return true;
    }
}