def yl3_4unaarne(n):
    if len(n) == 1:
        return n[0]
    else:
        return min(n[0], yl3_4unaarne(n[1:]))
    

def yl3_4binaarne(n):
    if len(n) == 1:
        return n[0]
    if len(n) == 2:
        return min(n[0], n[1])
    keskkoht = len(n)//2
    return min(yl3_4binaarne(n[0:keskkoht]), yl3_4binaarne(n[keskkoht:]))   

#n = [4,2,3,4,5]
#print(yl3_4unaarne(n))
#print(yl3_4binaarne(n))

def sum(i, j, summa):
    if i == len(j):
        if summa >= 100:
            return [summa]
        else:
            return []
    valikud = []
    valikud += sum(i + 1, j, summa + j[i]) 
    valikud += sum(i + 1, j, summa) 
    if valikud == []:
        return []
    return [min(valikud)]

#j = [50, 51, -1]
#print(sum(0, j, 0))

def pikim(a, i, p):
    if i == len(a):
        return p
    if a[i] > a[i - 1]:
        return pikim(a, i + 1, p + 1)
    else:
        return max(p, pikim(a, i + 1, 1))
    

#a = [1,2,3,1,2,3,4,2,3,4]
#print(pikim(a, 1, 1))


def perm(s, permutatsioon=""):
    if len(s) == 0:
        print(permutatsioon)
    else:
        for indeks in range(len(s)):
            perm(s[0:indeks] + s[indeks + 1:], permutatsioon+ s[indeks])

#perm("ATI")

def kombi(k, i, j, komb):
    if i == len(j):
        if k == 0:
            print(komb)
    else:
        if k > 0:
            kombi(k - 1, i + 1, j, komb + [j[i]])
        kombi(k, i + 1, j, komb)

#kombi(3, 0, [1,2,3,4,5], [])


def lahutused(n, tee):
    if n == 0:
        print(tee)
    else:
        if n >= 2:
            lahutused(n - 2, tee + [2])
        if n >= 4:
            lahutused(n - 4, tee + [4])
        if n >= 6:
            lahutused(n - 6, tee + [6])

lahutused(13, [])