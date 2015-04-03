namespace Q{
  namespace V{
    void f();
  }
  void V::f(){} //ok
  void V::g(){} //Error, g() is not yet a member of V
  namespace V{
    void g();// declaration of Q::V::g
  }
  namespace R{
    void Q::V::g(){}// Error, can't define Q::V::g inside R
  }
  void Q::V::g() {} //OK: global namespace enclose Q
}

void h(int);
namespace A{
  class X{
    friend void f(X);
    class Y{
      friend void g(); //A::g is a friend;
      friend void h(int); //A::h is a friend, no conflict with ::h
    };
  };

  //A::f, A::g and A::h are not visible at namespace scope
  X x;
  void g(){
    f(x); //A::X::f is found through ADL
  }
  void f(X){} //definition of A::f
}

namespace A{
  int x;
}

namespace B{
  int i;
  struct g{};
  struct x{};
  void f(int);
  void f(double);
  void g(char);// OK: function name g hides struct g
}

void func()
{
  int i;
  using B::i; // wrong, i declared twice
  void f(char);
  using B::f;
  f(3.5);//calls B::f(double)

  using B::g;
  g('a');
  struct g g1;

  using B::x;
  using A::x;

  x = 99;
  struct x x1;
}


namespace B{
  void f(int);
  voif f(double);
}
namespace C{
  void f(int);
  void f(double);
  void f(char);
}

void h()
{
  using B::f;
  using C::f;

  f('h');        // Calls C::f(char), correct
  f(1);          // Error: B::f(int) or C::f(int)
  void f(int);   // Error: Conflicts with C::f(int) and B::f(int)
}
