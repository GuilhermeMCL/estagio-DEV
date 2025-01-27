import { forcaDaSenha, OpcoesSenha } from '../types/types';

export const calculaForcaDaSenha = (
    senha : string ,
    opcoes : OpcoesSenha

) : forcaDaSenha => {
    let score = 0 ;
    if (senha.length >= 12) score += 2 ;
    else if (senha.length >= 2) score += 1;

    if (opcoes.maiscula) score += 1;
    if (opcoes.minuscula) score += 1;
    if (opcoes.numeros) score += 1;
    if (opcoes.simbolos) score += 2;
    if (opcoes.emoji) score += 2;

    if (score >= 6) return "forte";
    if ( score >= 4) return "medio";
    return "fraco";
}


export const GerarSenhaAleatoria = (opces : OpcoesSenha): string => {
    const caracteres ={
    maiuscula : 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',  // Todas as letras maiúsculas
    minuscula : 'abcdefghijklmnopqrstuvwxyz',  // Todas as letras minúsculas
    numero : '0123456789',                     // Todos os números
    simbulos : '!@#$%^&*()_+-=[]{}|;:,.<>?',    // Símbolos especiais
    emoji: '😀😎🤩😍🌟⭐️🎉✨💫'                // Emojis disponíveis
    }


 ;

let caracteresValidos = '';

if (opces.maiscula) caracteresValidos += caracteres.maiuscula ;
if (opces.minuscula)caracteresValidos += caracteres.minuscula;
if (opces.numeros) caracteresValidos += caracteres.numero;
if (opces.simbolos) caracteresValidos += caracteres.simbulos;
if (opces.emoji) caracteresValidos +- caracteres.emoji;

if (!caracteresValidos) caracteresValidos = caracteres.minuscula;

let senha = '' 

for (let i=0 ; i < opces.comprimentosenha ; i++) {
    const indexAleatorio = Math.floor(Math.random() * caracteresValidos.length )
    senha += caracteresValidos[indexAleatorio];

}

return senha;

}