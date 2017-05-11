// Generated from E:/IDEA/BaoSheng/BaoShengMES-Core/src/main/java/cc/oit/bsmes/generator/parser\CreateTable.g4 by ANTLR 4.x
package cc.oit.bsmes.generator.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CreateTableLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__19=1, T__18=2, T__17=3, T__16=4, T__15=5, T__14=6, T__13=7, T__12=8, 
		T__11=9, T__10=10, T__9=11, T__8=12, T__7=13, T__6=14, T__5=15, T__4=16, 
		T__3=17, T__2=18, T__1=19, T__0=20, RANGE=21, COMMENT=22, DESC=23, NUMBER=24, 
		ID=25, WS=26;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'"
	};
	public static final String[] ruleNames = {
		"T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", 
		"T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", 
		"T__2", "T__1", "T__0", "RANGE", "COMMENT", "DESC", "NUMBER", "ID", "WS", 
		"STUFF"
	};


	public CreateTableLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CreateTable.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\34\u00f7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3"+
		"\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\3\30\5\30\u00e0\n\30\3\31\6\31\u00e3\n\31\r\31\16\31"+
		"\u00e4\3\32\6\32\u00e8\n\32\r\32\16\32\u00e9\3\33\6\33\u00ed\n\33\r\33"+
		"\16\33\u00ee\3\33\3\33\3\34\6\34\u00f4\n\34\r\34\16\34\u00f5\2\2\35\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37"+
		"\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\2\3\2\6\3\2"+
		"\62;\5\2C\\aac|\5\2\13\f\17\17\"\"\6\2\13\f\17\17\"\"))\u00fa\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\39\3\2\2\2\5K\3\2\2\2\7N\3\2\2\2\tX\3"+
		"\2\2\2\13Z\3\2\2\2\r\\\3\2\2\2\17^\3\2\2\2\21f\3\2\2\2\23n\3\2\2\2\25"+
		"q\3\2\2\2\27s\3\2\2\2\31z\3\2\2\2\33\u0085\3\2\2\2\35\u0087\3\2\2\2\37"+
		"\u0098\3\2\2\2!\u00a9\3\2\2\2#\u00af\3\2\2\2%\u00b2\3\2\2\2\'\u00b7\3"+
		"\2\2\2)\u00b9\3\2\2\2+\u00c2\3\2\2\2-\u00c6\3\2\2\2/\u00df\3\2\2\2\61"+
		"\u00e2\3\2\2\2\63\u00e7\3\2\2\2\65\u00ec\3\2\2\2\67\u00f3\3\2\2\29:\7"+
		"e\2\2:;\7q\2\2;<\7o\2\2<=\7o\2\2=>\7g\2\2>?\7p\2\2?@\7v\2\2@A\7\"\2\2"+
		"AB\7q\2\2BC\7p\2\2CD\7\"\2\2DE\7e\2\2EF\7q\2\2FG\7n\2\2GH\7w\2\2HI\7o"+
		"\2\2IJ\7p\2\2J\4\3\2\2\2KL\7k\2\2LM\7u\2\2M\6\3\2\2\2NO\7V\2\2OP\7K\2"+
		"\2PQ\7O\2\2QR\7G\2\2RS\7U\2\2ST\7V\2\2TU\7C\2\2UV\7O\2\2VW\7R\2\2W\b\3"+
		"\2\2\2XY\7\60\2\2Y\n\3\2\2\2Z[\7+\2\2[\f\3\2\2\2\\]\7.\2\2]\16\3\2\2\2"+
		"^_\7E\2\2_`\7J\2\2`a\7C\2\2ab\7T\2\2bc\7*\2\2cd\7\63\2\2de\7+\2\2e\20"+
		"\3\2\2\2fg\7P\2\2gh\7W\2\2hi\7O\2\2ij\7G\2\2jk\7T\2\2kl\7K\2\2lm\7E\2"+
		"\2m\22\3\2\2\2no\7+\2\2op\7=\2\2p\24\3\2\2\2qr\7*\2\2r\26\3\2\2\2st\7"+
		"e\2\2tu\7t\2\2uv\7g\2\2vw\7c\2\2wx\7v\2\2xy\7g\2\2y\30\3\2\2\2z{\7e\2"+
		"\2{|\7q\2\2|}\7p\2\2}~\7u\2\2~\177\7v\2\2\177\u0080\7t\2\2\u0080\u0081"+
		"\7c\2\2\u0081\u0082\7k\2\2\u0082\u0083\7p\2\2\u0083\u0084\7v\2\2\u0084"+
		"\32\3\2\2\2\u0085\u0086\7=\2\2\u0086\34\3\2\2\2\u0087\u0088\7r\2\2\u0088"+
		"\u0089\7t\2\2\u0089\u008a\7k\2\2\u008a\u008b\7o\2\2\u008b\u008c\7c\2\2"+
		"\u008c\u008d\7t\2\2\u008d\u008e\7{\2\2\u008e\u008f\7\"\2\2\u008f\u0090"+
		"\7m\2\2\u0090\u0091\7g\2\2\u0091\u0092\7{\2\2\u0092\u0093\7\"\2\2\u0093"+
		"\u0094\7*\2\2\u0094\u0095\7K\2\2\u0095\u0096\7F\2\2\u0096\u0097\7+\2\2"+
		"\u0097\36\3\2\2\2\u0098\u0099\7e\2\2\u0099\u009a\7q\2\2\u009a\u009b\7"+
		"o\2\2\u009b\u009c\7o\2\2\u009c\u009d\7g\2\2\u009d\u009e\7p\2\2\u009e\u009f"+
		"\7v\2\2\u009f\u00a0\7\"\2\2\u00a0\u00a1\7q\2\2\u00a1\u00a2\7p\2\2\u00a2"+
		"\u00a3\7\"\2\2\u00a3\u00a4\7v\2\2\u00a4\u00a5\7c\2\2\u00a5\u00a6\7d\2"+
		"\2\u00a6\u00a7\7n\2\2\u00a7\u00a8\7g\2\2\u00a8 \3\2\2\2\u00a9\u00aa\7"+
		"v\2\2\u00aa\u00ab\7c\2\2\u00ab\u00ac\7d\2\2\u00ac\u00ad\7n\2\2\u00ad\u00ae"+
		"\7g\2\2\u00ae\"\3\2\2\2\u00af\u00b0\7\62\2\2\u00b0\u00b1\7+\2\2\u00b1"+
		"$\3\2\2\2\u00b2\u00b3\7F\2\2\u00b3\u00b4\7C\2\2\u00b4\u00b5\7V\2\2\u00b5"+
		"\u00b6\7G\2\2\u00b6&\3\2\2\2\u00b7\u00b8\7$\2\2\u00b8(\3\2\2\2\u00b9\u00ba"+
		"\7X\2\2\u00ba\u00bb\7C\2\2\u00bb\u00bc\7T\2\2\u00bc\u00bd\7E\2\2\u00bd"+
		"\u00be\7J\2\2\u00be\u00bf\7C\2\2\u00bf\u00c0\7T\2\2\u00c0\u00c1\7\64\2"+
		"\2\u00c1*\3\2\2\2\u00c2\u00c3\7*\2\2\u00c3\u00c4\5\61\31\2\u00c4\u00c5"+
		"\7+\2\2\u00c5,\3\2\2\2\u00c6\u00c7\7)\2\2\u00c7\u00c8\5\67\34\2\u00c8"+
		"\u00c9\7)\2\2\u00c9.\3\2\2\2\u00ca\u00cb\7p\2\2\u00cb\u00cc\7q\2\2\u00cc"+
		"\u00cd\7v\2\2\u00cd\u00ce\7\"\2\2\u00ce\u00cf\7p\2\2\u00cf\u00d0\7w\2"+
		"\2\u00d0\u00d1\7n\2\2\u00d1\u00e0\7n\2\2\u00d2\u00d3\7f\2\2\u00d3\u00d4"+
		"\7g\2\2\u00d4\u00d5\7h\2\2\u00d5\u00d6\7c\2\2\u00d6\u00d7\7w\2\2\u00d7"+
		"\u00d8\7n\2\2\u00d8\u00d9\7v\2\2\u00d9\u00da\7\"\2\2\u00da\u00db\7)\2"+
		"\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\5\67\34\2\u00dd\u00de\7)\2\2\u00de"+
		"\u00e0\3\2\2\2\u00df\u00ca\3\2\2\2\u00df\u00d2\3\2\2\2\u00e0\60\3\2\2"+
		"\2\u00e1\u00e3\t\2\2\2\u00e2\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e2"+
		"\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\62\3\2\2\2\u00e6\u00e8\t\3\2\2\u00e7"+
		"\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2"+
		"\2\2\u00ea\64\3\2\2\2\u00eb\u00ed\t\4\2\2\u00ec\u00eb\3\2\2\2\u00ed\u00ee"+
		"\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0"+
		"\u00f1\b\33\2\2\u00f1\66\3\2\2\2\u00f2\u00f4\n\5\2\2\u00f3\u00f2\3\2\2"+
		"\2\u00f4\u00f5\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f68"+
		"\3\2\2\2\b\2\u00df\u00e4\u00e9\u00ee\u00f5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}