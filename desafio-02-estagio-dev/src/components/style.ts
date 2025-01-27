import { styled } from "styled-components";


export const theme = {
  primary: '#2c3e50',    
  secondary: '#3498db',  
  background: '#f5f6fa', 
  text: '#2c3e50',      
  border: '#bdc3c7',    
  success: '#6bc045',   
  warning: '#ffd93d',   
  danger: '#ff6b6b',   
};
export const Container = styled.div`
  max-width: 600px;                           
  margin: 50px auto;                         
  padding: 20px;                              
  background-color: white;                    
  border-radius: 10px;                        
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);   
`;


export const Title = styled.h1`
  text-align: center;                         
  color: ${theme.primary};                    
  margin-bottom: 20px;                        
`;


export const PasswordDisplay = styled.div`
  background-color: ${theme.background};      
  padding: 15px;                             
  border-radius: 5px;                         
  margin-bottom: 20px;                       
  display: flex;                              
  justify-content: space-between;             
  align-items: center;                       
`;


export const PasswordText = styled.span`
  font-family: monospace;                     
  font-size: 1.2rem;                        
  word-break: break-all;                     
`;


export const Button = styled.button`
  background-color: ${theme.secondary};      
  color: white;                             
  border: none;                             
  padding: 10px 20px;                        
  border-radius: 5px;                        
  cursor: pointer;                           
  transition: background-color 0.3s;         
  display: flex;                             
  align-items: center;                       
  gap: 8px;                               

  &:hover {
    background-color: #2980b9;             
  }
`;


export const OptionsContainer = styled.div`
  display: flex;                            
  flex-direction: column;                    
  gap: 15px;                            
  margin-bottom: 20px;                 
`;


export const LengthControl = styled.div`
  display: flex;                           
  align-items: center;                     
  gap: 10px;                              
`;

export const CheckboxContainer = styled.label`
  display: flex;                           
  align-items: center;                      
  gap: 10px;                              
  cursor: pointer;                    
`;
export const StrengthMeter = styled.div<{ strength: 'fraco' | 'medio' | 'forte' }>`
  margin-top: 20px;                       
  padding: 10px;                           
  border-radius: 5px;                      
  text-align: center;                      
  
  background-color: ${({ strength }) => {
    switch (strength) {
      case 'fraco': return theme.danger;   
      case 'medio': return theme.warning; 
      case 'forte': return theme.success; 
    }
  }};
  color: ${({ strength }) => (strength === 'medio' ? theme.text : 'white')};
`;

