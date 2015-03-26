/* Generated By:JavaCC: Do not edit this line. PointerGrammar.java */
package org.xbrlapi.xpointer;

/**
DO NOT EDIT THIS FILE.
IT IS AUTO GENERATED BY JAVACC
*/
public class PointerGrammar implements PointerGrammarConstants {

  public static void main(String args[]) throws ParseException {
    String pointer = args[0];
    if (args.length > 1) {
        for (int i=1; i<args.length; i++) {
                pointer += " \u005ct\u005cn\u005cr" + args[i];
        }
    }
        java.io.StringReader sr = new java.io.StringReader(pointer);
        java.io.Reader r = new java.io.BufferedReader(sr);
        PointerGrammar parser = new PointerGrammar(r);
        java.util.Vector<PointerPart> pointerParts = parser.Pointer();
  }

  final public java.util.Vector<PointerPart> Pointer() throws ParseException {
        java.util.Vector<PointerPart> pointerParts = new java.util.Vector<PointerPart>();
        PointerPart p;
    if (jj_2_1(2)) {
      pointerParts = SchemeBased();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NCName:
        p = ShortHand();
                         pointerParts.add(p);
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    jj_consume_token(0);
	return pointerParts;
  }

  final public PointerPart ShortHand() throws ParseException {
        Token t;
        PointerPart p;
    t = jj_consume_token(NCName);
                p = new PointerPartImpl();
                p.setSchemeLocalName("element");
                p.setEscapedSchemeData(t.image);
                p.setUnescapedSchemeData(t.image);
                return p;
    }

  final public java.util.Vector<PointerPart> SchemeBased() throws ParseException {
        java.util.Vector<PointerPart> pointerParts = new java.util.Vector<PointerPart>();
        PointerPart p;
    p = PointerScheme();
         pointerParts.add(p);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NCName:
      case Space:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case Space:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_2;
        }
        jj_consume_token(Space);
      }
      p = PointerScheme();
                 pointerParts.add(p);
    }
         return pointerParts;
  }

  final public PointerPart PointerScheme() throws ParseException {
        String[] data;
        PointerPart p = new PointerPartImpl();
    SchemeName(p);
    jj_consume_token(LeftBracket);
    data = SchemeData();
                p.setEscapedSchemeData(data[0]);
                p.setUnescapedSchemeData(data[1]);
    jj_consume_token(RightBracket);
         return p;
    
  }

  final public void SchemeName(PointerPart p) throws ParseException {
    QName(p);
  }

  final public void QName(PointerPart p) throws ParseException {
        Token t;
    if (jj_2_2(2)) {
      t = jj_consume_token(NCName);
                 p.setSchemePrefix(t.image);
      jj_consume_token(1);
    } else {
      ;
    }
    t = jj_consume_token(NCName);
         p.setSchemeLocalName(t.image);
  }

  final public String[] SchemeData() throws ParseException {
        String[] newData;
        StringBuffer escapedData = new StringBuffer();
        StringBuffer unescapedData = new StringBuffer();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NormalChar:
      case LeftDataBracket:
      case EscapedChar:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      newData = EscapedData();
                        escapedData.append(newData[0]);
                        unescapedData.append(newData[1]);
    }
                String[] data = new String[2];
                data[0] = escapedData.toString();
                data[1] = unescapedData.toString();
                return data;
  }

  final public String[] EscapedData() throws ParseException {
        StringBuffer escapedData = new StringBuffer();
        StringBuffer unescapedData = new StringBuffer();
        String[] schemeData;
        Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NormalChar:
      t = jj_consume_token(NormalChar);
                                escapedData.append(t.image);
                                unescapedData.append(t.image);
      break;
    case EscapedChar:
      t = jj_consume_token(EscapedChar);
                                escapedData.append(t.image);
                                unescapedData.append(t.image.substring(1));
      break;
    case LeftDataBracket:
      t = jj_consume_token(LeftDataBracket);
                                        escapedData.append(t.image);
                                        unescapedData.append(t.image);
      schemeData = SchemeData();
                                        escapedData.append(schemeData[0]);
                                        unescapedData.append(schemeData[1]);
      t = jj_consume_token(RightBracket);
                                        escapedData.append(t.image);
                                        unescapedData.append(t.image);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                String[] data = new String[2];
                data[0] = escapedData.toString();
                data[1] = unescapedData.toString();
                return data;
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3_2() {
    if (jj_scan_token(NCName)) return true;
    if (jj_scan_token(1)) return true;
    return false;
  }

  private boolean jj_3R_4() {
    if (jj_3R_5()) return true;
    return false;
  }

  private boolean jj_3R_6() {
    if (jj_3R_7()) return true;
    return false;
  }

  private boolean jj_3R_5() {
    if (jj_3R_6()) return true;
    if (jj_scan_token(LeftBracket)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_4()) return true;
    return false;
  }

  private boolean jj_3R_7() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_2()) jj_scanpos = xsp;
    if (jj_scan_token(NCName)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public PointerGrammarTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[5];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40,0x8040,0x8000,0x2c,0x2c,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public PointerGrammar(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public PointerGrammar(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new PointerGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public PointerGrammar(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new PointerGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public PointerGrammar(PointerGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(PointerGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error {
	private static final long serialVersionUID = -4350472739507376751L; }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[16];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 5; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 16; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
