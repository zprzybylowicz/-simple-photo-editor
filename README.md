# -simple-photo-editor
 simple photo editor
To jest prosty edytor obrazów bitmapowych napisany w języku Java. Program wykorzystuje bibliotekę Swing i AWT do wyświetlania interfejsu. Pozwala na otwieranie obrazów w popularnych formatach, takich jak JPG, TIFF i BMP. Obraz jest wczytywany z dysku i przechowywany w postaci tablicy atrybutów w obiekcie klasy BufferedImage, gdzie każdy atrybut odpowiada pikselowi. Można manipulować wartościami składowych RGB i stosować filtry i maski.

Funkcje
Otwieranie obrazów: Możesz wczytać obrazy bitmapowe w formatach BMP, JPG i TIFF z komputera. Wystarczy kliknąć przycisk "Otwórz obraz" i wybrać plik z eksploratora.
Zapisywanie obrazów: Po dokonaniu edycji możesz zapisać obraz w formatach BMP, JPG lub TIFF. Po prostu wybierz odpowiedni format i kliknij przycisk "Zapisz obraz".
Edycja całego obrazu: Możesz wykonywać różne operacje na całym obrazie, takie jak rotacja, pojaśnianie/pociemnianie itp.
Rotacja: Możesz obracać obraz o 90 stopni. Operacja zamienia składowe x i y, wysokość staje się szerokością, a szerokość staje się wysokością. Składowe RGB na pozycji x, y są przenoszone na pozycję y, x.
Pojaśnianie/pociemnianie: Możesz zmieniać jasność obrazu, dodając (pojaśnianie) lub odejmując (pociemnianie) wartości od składowych RGB. Program dba o to, aby wartości kolorów nie przekraczały dopuszczalnego zakresu.
Edycja fragmentu obrazu: Możesz również dokonywać edycji wybranych fragmentów obrazu.

Zaznaczanie i wycięcie: Zaznacz interesujący cię obszar, klikając i przeciągając myszką. Gdy puścisz przycisk myszy, zostanie wyznaczony prostokątny obszar do wycięcia lub edycji. Zmodyfikowany fragment obrazu zostanie wyświetlony na ekranie.

Rysowanie pędzlem: Możesz zaznaczyć narzędzie "Pędzel" i klikając na obraz, narysować czarne kwadraty o rozmiarze 20x20 pikseli wokół klikniętego punktu. Wartość RGB pikseli zostanie ustawiona na czarny (000000).

Detekcja krawędzi: Możesz użyć operatora Sobela do detekcji krawędzi. Operator ten składa się z 8 masek 3x3, które pozwalają na wykrycie krawędzi pod różnymi kątami. Wynikowy obraz będzie zawierał wygładzone krawędzie.

Cofanie edycji: Program umożliwia cofanie wykonanych operacji. Możesz cofnąć ostatnią zmianę na obrazie lub anulować wiele ruchów, aż do stanu początkowego.

Uruchamianie programu
Aby uruchomić program, wykonaj następujące kroki:

Skompiluj kod źródłowy Java za pomocą kompilatora Javac.

javac EdytorObrazow.java
Uruchom program za pomocą maszyny wirtualnej Java (JVM).

Po uruchomieniu aplikacji pojawi się okno z interfejsem edytora obrazów.

Wybierz przycisk "Otwórz obraz", aby wczytać obraz z dysku.

Wykonuj różne operacje na obrazie, korzystając z dostępnych przycisków i narzędzi.

Możesz zapisać zmodyfikowany obraz, wybierając format (BMP, JPG, TIFF) i klikając przycisk "Zapisz obraz".

Aby cofnąć ostatnią operację, kliknij przycisk "Cofnij".

Aby zakończyć program, zamknij okno aplikacji.
