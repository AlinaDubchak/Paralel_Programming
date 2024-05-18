# Paralel_Programming 2024
During the study of discipline C, the skills of working with parallel programs, developing them and being able to synchronize them for correct work in the future were studied and mastered

The following languages were used to develop parallel algorithms:
- Java
- C++

The following topics were mastered:
- __`Threads` in Java and C++__
- __`Semaphores`, `Critical Sections`, `Atomic Variables`, `Barriers`, `Monitors` in Java__
- __`OpenMP` Library. `Barriers`, `Critical Sections`__

The parallel algorithms were developed on the example of the expressions below:

>   X = (B*Z)*(d*Z + R*(MO*MR))
>1) аі = ( Вн * Zн )                і= 1...4
>2) а = а + аі                         СР:а, копія
>3) МЕн = МRн* MO          СР: MO	 (ігноруємо за умовою)
>4) Cн = R * МЕн                СР: R (ігноруємо за умовою)
>5) Хн = а * d * Zн + а* Cн     СР: а, d, копія
---

>R = max(Z)*(B*MV) + e*X*(MM*MC) 
>1) ai = max(Zн),                                i = 1…4 
>2) a = max(a, ai),                               СР: а , копія
>3) Rн = a*(B*MVн) + e*X*(MM*MCн)     СР: а, B, e, MC, X,  копія а,е

---
>A = (R*MC)*MD*p + (B*Z)*E*d
>1.	 ai = (Bн*Zн)                  	      				i = 1...P
>2.	 a = a + ai                        	      				CP: a
>3.	Sн = R*MCн            					CP: R  
>4.	Aн = S*MDн*p + a*Eн*d         			СР: S, a, d, p

A more detailed algorithm for each of the streams, a diagram and a description of the interaction can be found in the folders provided for each work separately
