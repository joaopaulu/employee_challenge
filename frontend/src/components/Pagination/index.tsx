import { ReactComponent as ArrowIcon } from 'core/assets/images/arrow.svg';
import ReactPaginate from 'react-paginate';
import './styles.css';

type Props = {
  forcePage?: number;
  pageCount: number;
  range: number;
  onChange?: (pageNumber: number) => void;
};

const Pagination = ({ forcePage, pageCount, range, onChange }: Props) => {
  return (
    <div className="pagination-container">
      {pageCount > 1 && (
        <ReactPaginate
          forcePage={forcePage}
          pageCount={pageCount}
          pageRangeDisplayed={range}
          marginPagesDisplayed={1}
          containerClassName="pagination-container"
          pageLinkClassName="pagination-item"
          breakClassName="pagination-item"
          activeLinkClassName="pagination-link-active"
          previousLabel={
            <div className="pagination-arrow-container">
              <ArrowIcon />
            </div>
          }
          nextLabel={
            <div className="pagination-arrow-container">
              <ArrowIcon />
            </div>
          }
          previousClassName="arrow-previous"
          nextClassName="arrow-next"
          disabledClassName="arrow-inactive"
          onPageChange={items => (onChange ? onChange(items.selected) : {})}
        />
      )}
    </div>
  );
};

export default Pagination;
