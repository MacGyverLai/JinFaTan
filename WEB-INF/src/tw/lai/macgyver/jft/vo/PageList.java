package tw.lai.macgyver.jft.vo;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class PageList implements PaginatedList {
	
	private int fullListSize = 0;
	
	private int objectsPerPage = 0;
	
	private int pageNumber = 0;
	
	private String searchId = null;
	
	private String sortCriterion = null;
	
	private SortOrderEnum sortDirection = null;

	@Override
	public int getFullListSize() {
		// TODO Auto-generated method stub
		return this.fullListSize;
	}

	@Override
	public List getList() {
		// TODO Auto-generated method stub
		return null;
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
		return this.searchId;
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

	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

}
