def vähim(järjend):
    if len(järjend) == 1:
        return järjend[0]
    return min(järjend[0], vähim(järjend[1:]))

def vähim_bin(järjend):
    if len(järjend) == 1:
        return järjend[0]
    keskkoht = len(järjend) // 2
    vasem_alamjärjend = järjend[0:keskkoht]
    parem_alamjärjend = järjend[keskkoht:len(järjend)]
    vähim_vasemalt = vähim_bin(vasem_alamjärjend)
    vähim_paremalt = vähim_bin(parem_alamjärjend)
    return min(vähim_vasemalt, vähim_paremalt)
    #return min(vähim_bin(järjend[0:keskkoht]), vähim_bin(järjend[keskkoht:len(järjend)]))

j = [4,7,-9,54,10]
print(vähim_bin(j))

# n - mitu sümbolit on veel vaja lisada.
# ühtesid - mitu ühte on veel vaja lisada.
def bitivektor(n, ühtesid, vektor):
    if n == 0 and ühtesid == 0:
        print(vektor)
    elif n > 0:
        bitivektor(n - 1, ühtesid, vektor + '0')
        if ühtesid > 0:
            bitivektor(n - 1, ühtesid - 1, vektor + '1')

bitivektor(30, 2, "")

def alamhulgad(h):
    alamhulgad_rek(0, h, [])

def alamhulgad_rek(i, hulk, alamhulk):
    if i == len(hulk):
        print(alamhulk)
    else:
        alamhulgad_rek(i + 1, hulk, alamhulk + [hulk[i]])
        alamhulgad_rek(i + 1, hulk, alamhulk)

h = [1, 2, 3]
alamhulgad(h)

def lähim_summa(j):
    lähim = lähim_summa_rek(0, j, 0)
    if len(lähim) == 1:
        print(lähim[0])

def lähim_summa_rek(i, j, summa):
    if summa >= 100:
        return [summa]
    if i == len(j):
        return []
    valikud = []
    valikud += lähim_summa_rek(i + 1, j, summa + j[i])
    valikud += lähim_summa_rek(i + 1, j, summa)
    if valikud == []:
        return []
    return [min(valikud)]

j = [33, 3, 33, 33, 4]
lähim_summa(j)

def permutatsioonid(j):
    permutatsioonid_rek(j, "")

def permutatsioonid_rek(j, perm):
    if len(j) == 0:
        print(perm)
    else:
        for i in range(len(j)):
            sümbol = j[i]
            permutatsioonid_rek(j[:i] + j[i + 1:], perm + sümbol)

j = "abc"
permutatsioonid(j)