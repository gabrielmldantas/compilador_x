package br.com.ufs.xcompiler;

import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.text.MessageFormat;

import br.com.ufs.xcompiler.extensions.lexer.XCompilerLexer;
import br.com.ufs.xcompiler.lexer.Lexer;
import br.com.ufs.xcompiler.lexer.LexerException;
import br.com.ufs.xcompiler.node.EOF;
import br.com.ufs.xcompiler.node.InvalidToken;
import br.com.ufs.xcompiler.node.TBlank;
import br.com.ufs.xcompiler.node.Token;

public class Main {
	private static final int PUSHBACK_BUFFER_LENGTH = 1024;
	
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.err.println("Uso: java br.com.ufs.xcompiler.Main <file>");
			System.exit(-1);
		}
		
		PushbackReader reader = new PushbackReader(new FileReader(args[0]), PUSHBACK_BUFFER_LENGTH);
		try {
			XCompilerLexer lexer = new XCompilerLexer(reader);
			printTokens(lexer);
		} catch (XCompilerLexerException e) {
			String template = "[Line {0}, Pos {1}]: {2}";
			InvalidToken invalidToken = e.getInvalidToken();
			System.err.println(MessageFormat.format(template, invalidToken.getLine(), invalidToken.getPos(), e.getMessage()));
			System.err.println(invalidToken.getText());
		} finally {
			reader.close();
		}
	}
	
	private static void printTokens(Lexer lexer) throws IOException {
		try {
			Token token = lexer.next();
			Token lastToken = null;
			while (!(token instanceof EOF)) {
				String text;
				if (token instanceof TBlank) {
					text = token.getText();
				} else {
					text = extractTokenName(token.getClass().getSimpleName());
				}
				if (lastToken != null && !(token instanceof TBlank) && !(lastToken instanceof TBlank)) {
					text = " " + text;
				}
				System.out.print(text);
				lastToken = token;
				token = lexer.next();
			}
		} catch (LexerException e) {
			throw new XCompilerLexerException(e.getToken(), "Unknown token");
		}
	}
	
	private static String extractTokenName(String tokenClassName) {
		StringBuilder sb = new StringBuilder(tokenClassName);
		if (sb.charAt(0) == 'T') {
			sb.delete(0, 1);
		}
		sb.replace(0, 1, sb.substring(0, 1).toLowerCase());
		for (int i = 1; i < sb.length(); i++) {
			if (Character.isUpperCase(sb.charAt(i))) {
				String replacement = "_" + Character.toLowerCase(sb.charAt(i));
				sb.replace(i, i+1, replacement);
			}
		}
		return sb.toString();
	}
}
