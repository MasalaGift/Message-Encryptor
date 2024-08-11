package za.ac.tut.encryption;

public class MessageEncryptor {
    private static final int SHIFT = 3;

    public String encrypt(String plainText) {
        StringBuilder encryptedText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + SHIFT) % 26 + base);
            }
            encryptedText.append(c);
        }

        return encryptedText.toString();
    }
}

