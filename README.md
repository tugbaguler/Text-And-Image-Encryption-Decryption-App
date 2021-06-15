# Text-And-Image-Encryption-Decryption-App
An android app for encrypting and decrypting images and texts using AES and Base64.


Many different algorithms are used for encryption and decryption. Looking at algorithms such as DES, 3DES, AES algorithms, AES is the most secure and efficient, and one of the reasons is that the key length is longer than the key lengths of the other.

### AES Encryption and Decryption
AES is an abbreviation of Advanced Encryption Standard.
AES is used internationally as a crypto standard. It is a standard for the encryption of electronic data.
The encryption algorithm defined by AES is a symmetric and keyed algorithm in which the keys used for both encryption and decryption are related. The encryption and decryption keys are the same for AES.
![AES ALGO](https://javainterviewpoint.com/wp-content/uploads/2018/09/AES-Encryption-and-Decryption-in-Java.png)

### Application User Interfaces

![Splash Screen](https://github.com/tugbaguler/Text-And-Image-Encryption-Decryption-App/blob/main/ProjectUI/splash_screen.jpg)
![Main Page](https://github.com/tugbaguler/Text-And-Image-Encryption-Decryption-App/blob/main/ProjectUI/main_page.jpg)
![Encrypt Text](https://github.com/tugbaguler/Text-And-Image-Encryption-Decryption-App/blob/main/ProjectUI/encrypt_text.jpg)
![Decrypt Text](https://github.com/tugbaguler/Text-And-Image-Encryption-Decryption-App/blob/main/ProjectUI/decrypt_text.jpg)
![Image Encryption](https://github.com/tugbaguler/Text-And-Image-Encryption-Decryption-App/blob/main/ProjectUI/click_encrypt_buton_choose_image_and_encrypt.jpg)
![Image Decryption](https://github.com/tugbaguler/Text-And-Image-Encryption-Decryption-App/blob/main/ProjectUI/click_decrypt_button_and_you_can_see_the_image.jpg)

### Guide & Documentation for The Working Interface

* When the application is opened, the page where the algorithms will be tested appears. Users can test encryption on text and images using the AES algorithm.
* When the text button is clicked, the user is prompted to enter a text and set a password for this text. When the user fills in the fields and clicks on the encrypt button, the message is encrypted. When user clicks the Decrypt button, user can see the message again. But the most important point here is that the password does not change. Otherwise, the encrypted message cannot be decrypted.
* When the user clicks the Image button, user can upload any image in png and jpg format to the application by clicking the encrypt button. After the picture is selected, it should wait for a while because the picture is encrypted during this time. The encrypted text is shown to the user in the application. If the user wants to view the image, it is enough to click the decrypt button.
