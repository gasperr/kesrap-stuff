# kesrap-stuff
<h3>Predpostavke:</h3>
- učenec lahko ima nič ali več predmetov
- učenec lahko ima nič ali več učiteljev

- učitelj lahko ima nič ali več učencev
- učitelj lahko ima nič ali več predmetov

- predmet lahko ima nič ali več učencev
- predmet lahko ima nič ali enega učitelja

<h3>Predmet</h3>
pri 'uredi predmet':
- se lahko urejajo vsi aributi predmeta (naslov, opis, zacetek, konec)
- se lahko spremeni oz izbrise nosilec predmeta
- se lahko dodajo oz brišejo učenci

<h3>Ucenci</h3>
- pri dodajanju se izbere nič ali več predmetov
- pri urejanje se poleg osnovnih atributov lahko spremenijo tudi predmeti
- učitelji se posredno spreminjajo s predmetom

<h3>Ucitelji</h3>
- pri dodajanju se izbere nič ali več predmetov
- učenci se lahko dodajo skupaj s predmetom ali pa ne (če DA, se učitelju dodajo vsi učenci, ki so prijavljeni na predmet)
- učenci se posredno spreminjajo s predmetom

<h3>Ostalo</h3>
- datum se preverja če je pri vnosu formata: dd.mm.yyy, vendar ko se vnaša v bazo se pretvori v java.sql.date in nato pri branju ne nazaj v uttil.date, zato verjetno pri izpisovanju postane yyyy-mm-dd format
- uporabljene tehnologije: Maven, Hibernate, MySQL, JSF, Java EE 7
