package y_edit.editor;

import java.util.regex.*;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;


public class RegexRule implements IRule {

	IToken token;
	Pattern pattern;
	boolean spanNewLine = false;
	
	public RegexRule( String pattern, IToken token ){
		
		this.token = token;	
		this.pattern = Pattern.compile( pattern );
		
	}
	
	public IToken evaluate(ICharacterScanner scanner) {

		String stream = "";
		int c = scanner.read();
		int count = 1;

		while( c != ICharacterScanner.EOF  ){
					
			stream += (char) c;		
			
			Matcher m = pattern.matcher( stream );
			if( m.matches() ){
				return token;
			}
			
			if( !spanNewLine && ( '\n' == c || '\r' == c ) ){
				break;
			}
			
			count++;
			c = scanner.read();
		}

		//put the scanner back to the original position if no match
		for( int i = 0; i < count; i++){
			scanner.unread();
		}
		
		return Token.UNDEFINED;
		
	}

}
