package br.com.ufs.xcompiler.extensions.lexer;

import java.io.IOException;
import java.io.PushbackReader;

import br.com.ufs.xcompiler.XCompilerLexerException;
import br.com.ufs.xcompiler.lexer.Lexer;
import br.com.ufs.xcompiler.lexer.LexerException;
import br.com.ufs.xcompiler.node.EOF;
import br.com.ufs.xcompiler.node.InvalidToken;
import br.com.ufs.xcompiler.node.TComentBloco;
import br.com.ufs.xcompiler.node.TFimComentBloco;

public class XCompilerLexer extends Lexer {
	
	private int count;
	private StringBuffer text;
	private TComentBloco blkComment;
	
	public XCompilerLexer(PushbackReader in) {
		super(in);
	}
	
	@Override
	protected void filter() throws LexerException, IOException {
		if (state.equals(State.COMMENT)) {
			if (blkComment == null) {
				blkComment = (TComentBloco) token;
				text = new StringBuffer(blkComment.getText());
				count = 1;
				token = null;
			} else if (token instanceof EOF) {
				throw new XCompilerLexerException(new InvalidToken(text.toString(), blkComment.getLine(), blkComment.getPos()), "Unbalanced block comment");
			} else {
				text.append(token.getText());
				if (token instanceof TComentBloco) {
					count++;
				} else if (token instanceof TFimComentBloco) {
					count--;
				} 
				if (count != 0) {
					token = null;
				} else {
					blkComment.setText(text.toString());
					token = blkComment;
					state = State.NORMAL;
					blkComment = null;
				}
			}
		} else if (state.equals(State.NORMAL)) {
			if (token instanceof TFimComentBloco) {
				throw new XCompilerLexerException(new InvalidToken(token.getText(), token.getLine(), token.getPos()), "Invalid block comment end");
			}
		}
	}
}