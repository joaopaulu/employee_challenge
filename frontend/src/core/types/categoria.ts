export type CategoriasResponse = {
  content: Categorias[];
  totalPages: number;
};

export type Categorias = {
  id: number;
  descricao: string;
};
