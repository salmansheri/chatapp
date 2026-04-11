import { defineConfig } from '@hey-api/openapi-ts';

export default defineConfig({
  input: './chatapi.json', 
  output: "./src/generated",
  plugins: [
    "@tanstack/react-query"
  ]
});