import React, { useState, useCallback } from 'react';
import { Copy, RefreshCw } from 'lucide-react';
import * as S from './style';
import { OpcoesSenha } from '../types/types';
import { calculaForcaDaSenha, GerarSenhaAleatoria } from '../utils/utils';

// Componente principal do gerador de senhas
const GeradorDeSenha: React.FC = () => {
  // Estado para armazenar a senha gerada
  const [password, setPassword] = useState('');
  
  // Estado para armazenar as opções de geração de senha
  const [options, setOptions] = useState<OpcoesSenha>({
    comprimentosenha: 12,
    maiscula: true,
    minuscula: true,
    numeros: true,
    simbolos: false,
    emoji: false,
  });


  const forcaDaSenha = calculaForcaDaSenha(password, options);

  const handleGeneratePassword = useCallback(() => {
    const newPassword = GerarSenhaAleatoria(options);
    setPassword(newPassword);
  }, [options]);


  const handleCopyPassword = useCallback(() => {
    navigator.clipboard.writeText(password);
    alert('Senha copiada para a área de transferência!');
  }, [password]);

 
  const handleOptionChange = useCallback((option: keyof OpcoesSenha) => {
    setOptions(prev => ({
      ...prev,
      [option]: !prev[option]
    }));
  }, []);

  return (
    <S.Container>
      <S.Title>Gerador de Senhas</S.Title>

      <S.PasswordDisplay>
        <S.PasswordText>{password || 'Clique em gerar senha'}</S.PasswordText>
        <div style={{ display: 'flex', gap: '8px' }}>
          <S.Button onClick={handleGeneratePassword}>
            <RefreshCw size={16} />
            Gerar
          </S.Button>
          <S.Button onClick={handleCopyPassword} disabled={!password}>
            <Copy size={16} />
            Copiar
          </S.Button>
        </div>
      </S.PasswordDisplay>


      <S.OptionsContainer>
        <S.LengthControl>
          <label htmlFor="length">Comprimento:</label>
          <input
            type="range"
            id="length"
            min="4"
            max="32"
            value={options.comprimentosenha}
            onChange={e => setOptions(prev => ({
              ...prev,
              length: parseInt(e.target.value)
            }))}
          />
          <span>{options.comprimentosenha}</span>
        </S.LengthControl>

        <S.CheckboxContainer>
          <input
            type="checkbox"
            id="uppercase"
            checked={options.maiscula}
            onChange={() => handleOptionChange('maiscula')}
          />
          <span>Incluir letras maiúsculas</span>
        </S.CheckboxContainer>

        <S.CheckboxContainer>
          <input
            type="checkbox"
            id="lowercase"
            checked={options.minuscula}
            onChange={() => handleOptionChange('minuscula')}
          />
          <span>Incluir letras minúsculas</span>
        </S.CheckboxContainer>

        <S.CheckboxContainer>
          <input
            type="checkbox"
            id="numbers"
            checked={options.numeros}
            onChange={() => handleOptionChange('numeros')}
          />
          <span>Incluir números</span>
        </S.CheckboxContainer>

        <S.CheckboxContainer>
          <input
            type="checkbox"
            id="symbols"
            checked={options.simbolos}
            onChange={() => handleOptionChange('simbolos')}
          />
          <span>Incluir símbolos</span>
        </S.CheckboxContainer>

        <S.CheckboxContainer>
          <input
            type="checkbox"
            id="emoji"
            checked={options.emoji}
            onChange={() => handleOptionChange('emoji')}
          />
          <span>Incluir emoji</span>
        </S.CheckboxContainer>
      </S.OptionsContainer>

 
      {password && (
        <S.StrengthMeter strength={forcaDaSenha}>
          Força da senha: {forcaDaSenha.toUpperCase()}
        </S.StrengthMeter>
      )}
    </S.Container>
  );
};

export default GeradorDeSenha;