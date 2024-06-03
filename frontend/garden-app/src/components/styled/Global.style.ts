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
