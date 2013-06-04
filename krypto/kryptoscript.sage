def helpfunc():
  print "Available Functions: wiener2, pminus1, disc_log_diffie"
  print "wiener2 takes two arguments: n and e"
  print "pminus1 takes three arguments: n, B, a"
  print "disc_log_diffie takes four arguments: p, w(Omega), a, b"
  print "fermatfactor takes one argument: n"


def wiener2 ( n, e):
    contFrac = continued_fraction_list( e/n, partial_convergents=True)
    parConv = contFrac[1]
    print "number of partial convergents = ", len(parConv)
    del parConv[0]
    L = []
    for i in parConv:
        h = (e*i[1]-1)
        if h%i[0] == 0:
            phi = h//i[0]
            L.append(phi)
    print "number of candidates = ", len(L)
    print L
    for i in L:
        a = n - i + 1
        b = sqrt(a*a-4*n)
        if type(b) == sage.rings.integer.Integer:
            p = (a + b)/2
            q = n/p
            break
    return [p,q]

def pminus1(n,B,a):
  k = factorial(B)
  b = a.powermod(k,n)
  return gcd(b-1,n)

      
def fermatfactor(N):
  if N <= 0: return [N]
  if is_even(N): return [2,N/2]
  a = ceil(sqrt(N))
  while not is_square(a^2-N):
    a = a + 1
  b = sqrt(a^2-N)
  return [a - b,a + b]


def disc_log_diffie(p,w,a,b):
  for i in range(p):
    if w.powermod(i,p) == a:
      print i
      break
