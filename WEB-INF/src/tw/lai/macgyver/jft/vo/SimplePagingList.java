package tw.lai.macgyver.jft.vo;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class SimplePagingList implements PaginatedList {

	private List data = null;
	
	private int fullListSize = 0;
	
	private String sortCriterion = null;
	
	private SortOrderEnum sortDirection = null;
	
	private int pageNumber = 1;
	
	private int objectsPerPage = 30;
	
	@Override
	public int getFullListSize() {
		// TODO Auto-generated method stub
		return this.fullListSize;
	}

	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	@Override
	public List getList() {
		// TODO Auto-generated method stub
		return this.data;
	}

	@Override
	public int getObjectsPerPage() {
		// TODO Auto-generated method stub
		return this.objectsPerPage;
	}

	@Override
	public int getPageNumber() {
		// TODO Auto-generated method stub
		return this.pageNumber;
	}

	@Override
	public String getSearchId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSortCriterion() {
		// TODO Auto-generated method stub
		return this.sortCriterion;
	}

	@Override
	public SortOrderEnum getSortDirection() {
		// TODO Auto-generated method stub
		return this.sortDirection;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	public void setList(List list) {
		this.data = list;
	}
}
