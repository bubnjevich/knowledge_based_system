import { createGlobalStyle, styled } from "styled-components";

export const GlobalStyles = createGlobalStyle`
  *,
  *::before,
  *::after {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    
    font-family: monospace;
    overflow-x: hidden;
    height: 100%;
    font-size: 16px;
  }
`;

export const FormSection = styled.div`
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const WelcomeSection = styled.div`
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2em;
  background-repeat: no-repeat;
`;

export const LoginContainer = styled.div`
  display: flex;
  min-height: 75vh;
  margin-left: 200px;
  margin-right: 200px;
  margin-top: 30px;
  margin-bottom: 30px;
  background: #393e46;
  color: #fff;
`;

export const CenteredContainer = styled.div`
  background: #393e46;
  color: #fff;
  box-shadow: 50px 50px 101px -49px rgba(0, 0, 0, 0.86);
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 20px;
  border-radius: 10px;
`;

export const SimpleContainer = styled.div`
  background: #393e46;
  color: #fff;
  box-shadow: 50px 50px 101px -49px rgba(0, 0, 0, 0.86);
  display: flex;
  flex-direction: column;
  height: auto;
  width: 35%;
  padding: 3rem;
  border-radius: 10px;
`;

export const CenteredDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80vh; /* adjust as needed */
  text-align: center;
`;


export const TableContainer = styled.div`
  margin: 20px;
`;

export const StyledTable = styled.table`
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
`;

export const StyledTh = styled.th`
  background-color: #4ecca3;
  color: white;
  padding: 10px;
  text-align: left;
`;

export const StyledTd = styled.td`
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
`;

export const StyledTr = styled.tr`
  cursor: pointer;
  &:nth-child(even) {
    background-color: #f2f2f2;
    color: black;
  },
`;

export const TableHeader = styled.thead`
  ${StyledTr} {
    ${StyledTh} {
      font-weight: bold;
      text-align: left;
    }
  }
`;

export const TableBody = styled.tbody`
  ${StyledTr} {
    ${StyledTd} {
      text-align: left;
    }
  }
`;

export const StyledCaption = styled.caption`
  font-size: 1.5em;
  margin-bottom: 10px;
  font-weight: bold;
`;

export const FormWrapper = styled.div`
  width: 70%;
`;

export const FormLabel = styled.label`
  margin-bottom: 0.5rem;
`;

export const FormInput = styled.input`
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
`;

export const FormSelect = styled.select`
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
`;

export const FormButton = styled.button`
  background-color: #4caf50;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background-color: #45a049;
  }
`;

