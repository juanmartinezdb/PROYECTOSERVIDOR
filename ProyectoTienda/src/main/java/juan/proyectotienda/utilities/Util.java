package juan.proyectotienda.utilities;


import juan.proyectotienda.dao.UsuarioDAO;
import juan.proyectotienda.dao.UsuarioDAOImpl;
import juan.proyectotienda.model.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Util {
    public static String hashPassword(String password ) throws NoSuchAlgorithmException {
        MessageDigest digest;

        digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(encodedhash);

    }
    private static String bytesToHex(byte[] byteHash) {

        StringBuilder hexString = new StringBuilder(2 * byteHash.length);
        for (int i = 0; i < byteHash.length; i++) {
            String hex = Integer.toHexString(0xff & byteHash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();

    }

    public static Optional<Usuario> autentificacion (Usuario user){
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        Optional<Usuario> registrado = usuarioDAO.getByPassword(user.getPassword(), user.getNombre());
        if (registrado.isPresent()) {
            System.out.println(registrado.get().getNombre()+" encontrado");
            return registrado;
        } else {
            System.out.println("no encontrado");
            return registrado;
        }
    }
}
