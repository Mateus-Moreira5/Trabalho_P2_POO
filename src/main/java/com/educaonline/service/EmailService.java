package main.java.com.educaonline.service;

public class EmailService {
    
    public static boolean enviarEmailBoasVindas(String email, String nome) {
        String assunto = "Bem-vindo ao EducaOnline!";
        String mensagem = String.format(
            "Olá %s,\n\n" +
            "Seja muito bem-vindo à plataforma EducaOnline!\n" +
            "Estamos felizes em tê-lo conosco.\n\n" +
            "Acesse sua área do aluno e comece seus estudos hoje mesmo!\n\n" +
            "Atenciosamente,\nEquipe EducaOnline",
            nome
        );
        
        return enviarEmail(email, assunto, mensagem);
    }
    
    public static boolean enviarEmailMatricula(String email, String nome, String curso) {
        String assunto = "Confirmação de Matrícula - EducaOnline";
        String mensagem = String.format(
            "Olá %s,\n\n" +
            "Sua matrícula no curso \"%s\" foi confirmada com sucesso!\n\n" +
            "Acesse a plataforma para começar suas aulas.\n\n" +
            "Atenciosamente,\nEquipe EducaOnline",
            nome, curso
        );
        
        return enviarEmail(email, assunto, mensagem);
    }
    
    public static boolean enviarEmailCertificado(String email, String nome, String curso) {
        String assunto = "Certificado Disponível - EducaOnline";
        String mensagem = String.format(
            "Parabéns %s!\n\n" +
            "Seu certificado do curso \"%s\" está disponível para download.\n\n" +
            "Acesse sua área do aluno para emitir seu certificado.\n\n" +
            "Atenciosamente,\nEquipe EducaOnline",
            nome, curso
        );
        
        return enviarEmail(email, assunto, mensagem);
    }
    
    private static boolean enviarEmail(String destinatario, String assunto, String mensagem) {
        System.out.println("=== EMAIL ENVIADO ===");
        System.out.println("Para: " + destinatario);
        System.out.println("Assunto: " + assunto);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("=====================");
        return true;
    }
}