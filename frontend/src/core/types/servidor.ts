import {Categorias} from "./categoria";

export type ServidoresResponse = {
  content: Servidor[];
  totalPages: number;
};

export type Servidor = {
  id: number;
  nome: string;
  matricula: string;
  lotacao: string;
  categoria:Categorias;
};
